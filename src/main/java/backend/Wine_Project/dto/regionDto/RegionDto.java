package backend.Wine_Project.dto.regionDto;

import backend.Wine_Project.model.wine.Wine;

public record RegionDto(
        String name,
        Wine wine
) {
}
