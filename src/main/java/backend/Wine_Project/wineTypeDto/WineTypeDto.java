package backend.Wine_Project.wineTypeDto;

import backend.Wine_Project.model.Wine;
import backend.Wine_Project.model.WineType;

public record WineTypeDto(
        String name,
        Wine wine
) {
}
