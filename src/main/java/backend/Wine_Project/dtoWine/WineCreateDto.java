package backend.Wine_Project.dtoWine;

import backend.Wine_Project.model.GrapeVarieties;
import backend.Wine_Project.model.Region;
import backend.Wine_Project.model.WineType;

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
