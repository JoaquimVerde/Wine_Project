package backend.Wine_Project.dtoWine;

import backend.Wine_Project.model.WineType;

import java.time.Year;

public record WineReadRatingDto(

        String name,
        WineType wineType,
        Year year
) {
}
