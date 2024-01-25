package backend.Wine_Project.converter.wineConverters;

import backend.Wine_Project.dto.grapeVarietiesDto.GrapeVarietiesDto;
import backend.Wine_Project.model.wine.GrapeVarieties;

import java.util.List;

public class GrapeVarietiesConverter {
    public static GrapeVarietiesDto fromGrapeVarietiesToGrapeVarietiesDto(GrapeVarieties grapeVarieties){
        return new GrapeVarietiesDto(grapeVarieties.getName(), grapeVarieties.getWine());
    }
    public static GrapeVarieties fromGrapeVarietiesDtoToGrapeVarieties(GrapeVarietiesDto grapeVarietiesDto){
        return new GrapeVarieties(grapeVarietiesDto.name(),grapeVarietiesDto.wine());
    }
    public static List<GrapeVarietiesDto> fromGrapeVarietiesListToGrapeVarietiesDtoList(List<GrapeVarieties> grapeVarietiesList){
        return grapeVarietiesList.stream()
                .map(GrapeVarietiesConverter::fromGrapeVarietiesToGrapeVarietiesDto)
                .toList();
    }
}
