package backend.Wine_Project.dto.wineDto;

import backend.Wine_Project.model.wine.GrapeVarieties;
import backend.Wine_Project.model.wine.Region;
import backend.Wine_Project.model.wine.WineType;

import java.time.Year;
import java.util.List;

public record WineCreateDto(
        String name,
        WineType wineType,
        Region region,
        List<GrapeVarieties> grapeVarietiesList,
        double price,
        double alcohol,
        Year year
) {
}
