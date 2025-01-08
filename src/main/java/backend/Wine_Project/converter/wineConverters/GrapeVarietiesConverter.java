package backend.Wine_Project.converter.wineConverters;

import backend.Wine_Project.dto.grapeVarietiesDto.GrapeVarietiesDto;
import backend.Wine_Project.model.wine.GrapeVarieties;

import java.util.Set;
import java.util.stream.Collectors;

public class GrapeVarietiesConverter {
    public static GrapeVarietiesDto fromGrapeVarietiesToGrapeVarietiesDto(GrapeVarieties grapeVarieties) {
        return new GrapeVarietiesDto(grapeVarieties.getName());
    }

    public static GrapeVarieties fromGrapeVarietiesDtoToGrapeVarieties(GrapeVarietiesDto grapeVarietiesDto) {
        return new GrapeVarieties(grapeVarietiesDto.name());
    }

    public static Set<GrapeVarietiesDto> fromGrapeVarietiesListToGrapeVarietiesDtoList(Set<GrapeVarieties> grapeVarietiesList) {
        return grapeVarietiesList.stream()
                .map(GrapeVarietiesConverter::fromGrapeVarietiesToGrapeVarietiesDto)
                .collect(Collectors.toSet());
    }

}
