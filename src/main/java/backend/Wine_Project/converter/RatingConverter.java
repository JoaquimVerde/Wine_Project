package backend.Wine_Project.converter;

import backend.Wine_Project.model.Rating;
import backend.Wine_Project.dtoRating.RatingReadDto;

import java.util.List;

public class RatingConverter {

    public static RatingReadDto fromModelToRatingReadDto (Rating rating){
        return new RatingReadDto(
                ClientConverter.fromModelToClientReadRatingDto(rating.getClient()),
                WineConverter.fromWineToWineReadRatingDto(rating.getWine()),
                rating.getRate()
        );
    }

    public static List<RatingReadDto> fromModelListToRatingReadDtoList(List<Rating> ratings){
        return ratings.stream().map(RatingConverter::fromModelToRatingReadDto).toList();
    }


}
