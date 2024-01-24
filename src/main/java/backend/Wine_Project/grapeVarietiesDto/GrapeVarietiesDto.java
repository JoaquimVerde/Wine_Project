package backend.Wine_Project.grapeVarietiesDto;

import backend.Wine_Project.model.Wine;

public record GrapeVarietiesDto(
        String name,
        Wine wine
) {
}
