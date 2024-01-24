package backend.Wine_Project.dtoRating;

import backend.Wine_Project.dtoClient.ClientReadRatingDto;
import backend.Wine_Project.dtoWine.WineReadRatingDto;

public record RatingReadDto(

        ClientReadRatingDto client,
        WineReadRatingDto wine,
        double rating
) {
}
