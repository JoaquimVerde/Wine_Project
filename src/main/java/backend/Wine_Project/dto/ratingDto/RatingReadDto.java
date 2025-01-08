package backend.Wine_Project.dto.ratingDto;

import backend.Wine_Project.dto.clientDto.ClientReadRatingDto;
import backend.Wine_Project.dto.wineDto.WineReadRatingDto;

public record RatingReadDto(

        ClientReadRatingDto client,
        WineReadRatingDto wine,
        double rate,
        String review
) {
}
