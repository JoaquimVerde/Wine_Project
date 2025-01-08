package backend.Wine_Project.converter.wineConverters;


import backend.Wine_Project.dto.wineDto.WineReadDto;
import backend.Wine_Project.dto.wineDto.WineReadRatingDto;
import backend.Wine_Project.model.wine.Wine;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class WineConverter {


    public static WineReadDto fromWineToWineReadDto(Wine wine) {
        return new WineReadDto(
                wine.getName(),
                WineTypeConverter.fromWineTypeToWineTypeDto(wine.getWineType()),
                GrapeVarietiesConverter.fromGrapeVarietiesListToGrapeVarietiesDtoList(wine.getGrapeVarietiesList()),
                RegionConverter.fromRegionToRegionDto(wine.getRegion()),
                wine.getPrice(),
                wine.getAlcohol(),
                wine.getYear(),
                wine.getRatingAvg()
        );
    }

    public static List<WineReadDto> fromListOfWinesToListOfWinesReadDto(List<Wine> wineList) {
        return wineList.stream().map(WineConverter::fromWineToWineReadDto).toList();
    }

    public static WineReadRatingDto fromWineToWineReadRatingDto(Wine wine) {
        return new WineReadRatingDto(
                wine.getName(),
                WineTypeConverter.fromWineTypeToWineTypeDto(wine.getWineType()),
                wine.getYear()
        );
    }

    public static Set<WineReadDto> fromSetOfWinesToSetOfWinesReadDto(Set<Wine> wines) {
        return wines.stream().map(WineConverter::fromWineToWineReadDto).collect(Collectors.toSet());
    }


}
