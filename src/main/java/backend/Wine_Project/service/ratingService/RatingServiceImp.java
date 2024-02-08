package backend.Wine_Project.service.ratingService;

import backend.Wine_Project.converter.RatingConverter;
import backend.Wine_Project.dto.ratingDto.RatingCreateDto;
import backend.Wine_Project.exceptions.alreadyExists.RatingAlreadyExistsException;
import backend.Wine_Project.model.Client;
import backend.Wine_Project.model.Rating;
import backend.Wine_Project.dto.ratingDto.RatingReadDto;
import backend.Wine_Project.model.wine.Wine;
import backend.Wine_Project.repository.RatingRepository;
import backend.Wine_Project.service.LMStudioService;
import backend.Wine_Project.service.wineService.WineService;
import backend.Wine_Project.service.clientService.ClientService;
import backend.Wine_Project.util.Messages;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;


@Service
public class RatingServiceImp implements RatingService {
    private final RatingRepository ratingRepository;

    private final ClientService clientService;

    private final WineService wineService;

    private final LMStudioService lmStudioService;


    @Autowired
    public RatingServiceImp(RatingRepository ratingRepository, ClientService clientService, WineService wineService, LMStudioService lmStudioService) {
        this.ratingRepository = ratingRepository;
        this.clientService = clientService;
        this.wineService = wineService;
        this.lmStudioService = lmStudioService;
    }


    @Override
    public List<RatingReadDto> getAll() {
        List<Rating> ratings = ratingRepository.findAll();
        return RatingConverter.fromModelListToRatingReadDtoList(ratings);
    }

    @Override
    public Long create(RatingCreateDto rating) {

        Client client = clientService.getById(rating.clientId());
        Wine wine = wineService.getById(rating.wineId());
        Optional<Rating> optionalRating = ratingRepository.findByClientAndWine(client, wine);
        if (optionalRating.isPresent())
            throw new RatingAlreadyExistsException(Messages.RATING_ALREADY_EXISTS.getMessage());

        Rating ratingToAdd = new Rating(client, wine, rating.rate());
        ratingRepository.save(ratingToAdd);

        double ratingAvg = ratingRepository.getAverageRatingByWine(wine);
        wine.setRatingAvg(ratingAvg);
        wine.setRated(true);
        wineService.saveWine(wine);
        client.getRatedWines().add(wine);
        clientService.saveClient(client);

        String review = lmStudioService.callLocalLMStudio("Make me a small wine review, maximum of 30 words, based on the following information: " +
                "name: " + wine.getName() + ", color: " + wine.getWineType().getName() + ", year: " + wine.getYear());

        try {
            JSONObject jsonObject = new JSONObject(review);
            JSONArray choicesArray = jsonObject.getJSONArray("choices");
            JSONObject firstChoice = choicesArray.getJSONObject(0);
            String textValue = firstChoice.getString("text");
            ratingToAdd.setReview(textValue);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        ratingRepository.save(ratingToAdd);

        return ratingToAdd.getId();
    }


}
