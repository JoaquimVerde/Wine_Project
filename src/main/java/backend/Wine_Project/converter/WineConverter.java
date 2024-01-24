package backend.Wine_Project.converter;

import backend.Wine_Project.dtoWine.WineCreateDto;
import backend.Wine_Project.dtoWine.WineReadRatingDto;
import backend.Wine_Project.model.Wine;

import java.util.List;

public class WineConverter {
    public static WineCreateDto fromWineToWineCreateDto(Wine wine) {
        return new WineCreateDto(
                wine.getName(),
                wine.getWineType(),
                wine.getRegion(),
                wine.getGrapeVarietiesList(),
                wine.getPrice(),
                wine.getAlcohol(),
                wine.getYear()
        );
    }

    public static List<WineCreateDto> fromListOfWinesToListOfWinesCreateDto(List<Wine> carList) {
        return carList.stream().map(WineConverter::fromWineToWineCreateDto).toList();
    }

    public static Wine fromWineCreateDtoToWine(WineCreateDto wine) {
        return new Wine(
                wine.name(),
                wine.wineType(),
                wine.region(),
                wine.grapeVarietiesList(),
                wine.price(),
                wine.alcohol(),
                wine.year());
    }

    public static WineReadRatingDto fromWineToWineReadRatingDto(Wine wine) {
        return new WineReadRatingDto(
                wine.getName(),
                wine.getWineType(),
                wine.getYear()
        );
    }
}
