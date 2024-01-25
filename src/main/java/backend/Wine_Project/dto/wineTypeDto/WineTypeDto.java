package backend.Wine_Project.dto.wineTypeDto;

import backend.Wine_Project.model.wine.Wine;

public record WineTypeDto(
        String name,
        Wine wine
) {
}
