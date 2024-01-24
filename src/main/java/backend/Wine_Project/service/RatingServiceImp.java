package backend.Wine_Project.service;

import backend.Wine_Project.converter.RatingConverter;
import backend.Wine_Project.dtoRating.RatingCreateDto;
import backend.Wine_Project.model.Client;
import backend.Wine_Project.model.Rating;
import backend.Wine_Project.dtoRating.RatingReadDto;
import backend.Wine_Project.model.Wine;
import backend.Wine_Project.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RatingServiceImp implements RatingService {
    private final RatingRepository ratingRepository;

    private final ClientService clientService;

    private final WineService wineService;




    @Autowired
    public RatingServiceImp(RatingRepository ratingRepository, ClientService clientService, WineService wineService) {
        this.ratingRepository = ratingRepository;
        this.clientService = clientService;
        this.wineService = wineService;
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
        double ratingAvg = ratingRepository.getAverageRatingByWine(wine.getId());
        wine.setRatingAvg(ratingAvg);

        Rating ratingToAdd = new Rating(client, wine, rating.rate());
        ratingRepository.save(ratingToAdd);
        return ratingToAdd.getId();
    }


}
