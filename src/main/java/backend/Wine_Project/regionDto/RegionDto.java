package backend.Wine_Project.regionDto;

import backend.Wine_Project.model.Wine;

public record RegionDto(
        String name,
        Wine wine
) {
}
