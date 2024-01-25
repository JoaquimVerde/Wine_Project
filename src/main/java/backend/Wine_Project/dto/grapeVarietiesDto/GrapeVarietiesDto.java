package backend.Wine_Project.dto.grapeVarietiesDto;

import backend.Wine_Project.model.wine.Wine;

public record GrapeVarietiesDto(
        String name,
        Wine wine
) {
}
