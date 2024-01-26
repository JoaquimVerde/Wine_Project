package backend.Wine_Project.dto.wineDto;

import backend.Wine_Project.dto.grapeVarietiesDto.GrapeVarietiesDto;
import backend.Wine_Project.dto.regionDto.RegionCreateDto;
import backend.Wine_Project.dto.wineTypeDto.WineTypeCreateDto;
import backend.Wine_Project.model.wine.GrapeVarieties;
import backend.Wine_Project.model.wine.Region;
import backend.Wine_Project.model.wine.WineType;

import java.time.Year;
import java.util.Set;

public record WineReadDto(

        String name,
        WineTypeCreateDto wineType,
        Set<GrapeVarietiesDto> grapeVarietiesId,
        RegionCreateDto region,
        double price,
        double alcohol,
        int year
) {
}
