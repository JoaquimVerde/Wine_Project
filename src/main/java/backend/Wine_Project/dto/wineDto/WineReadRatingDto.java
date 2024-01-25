package backend.Wine_Project.dto.wineDto;

import backend.Wine_Project.model.wine.WineType;

import java.time.Year;

public record WineReadRatingDto(

        String name,
        WineType wineType,
        int year
) {
}
