package backend.Wine_Project.converter.wineConverters;

import backend.Wine_Project.dto.wineDto.WineCreateDto;
import backend.Wine_Project.dto.wineDto.WineReadDto;
import backend.Wine_Project.dto.wineDto.WineReadRatingDto;
import backend.Wine_Project.model.wine.Wine;

import java.util.List;

public class WineConverter {


    public static WineReadDto fromWineToWineReadDto(Wine wine) {
        return new WineReadDto(
                wine.getName(),
                wine.getWineType(),
                wine.getGrapeVarietiesList(),
                wine.getRegion(),
                wine.getPrice(),
                wine.getAlcohol(),
                wine.getYear()
        );
    }
    public static List<WineReadDto> fromListOfWinesToListOfWinesReadDto(List<Wine> carList) {
        return carList.stream().map(WineConverter::fromWineToWineReadDto).toList();
    }


    /*public static Wine fromWineCreateDtoToWine(WineCreateDto wine) {
        return new Wine(
                wine.name(),
                wine.wineType(),
                wine.region(),
                wine.price(),
                wine.alcohol(),
                wine.year());
    }*/

    public static WineReadRatingDto fromWineToWineReadRatingDto(Wine wine) {
        return new WineReadRatingDto(
                wine.getName(),
                wine.getWineType(),
                wine.getYear()
        );
    }


}