package backend.Wine_Project.dto.wineDto;

import backend.Wine_Project.model.wine.GrapeVarieties;
import backend.Wine_Project.model.wine.Region;
import backend.Wine_Project.model.wine.WineType;
import jakarta.validation.constraints.*;

import java.time.Year;
import java.util.List;
import java.util.Set;

public record WineCreateDto(

        @NotNull
        @NotBlank
        String name,
        @NotNull
        @Min(value = 1)
        Long wineTypeId,
        @NotNull
        @Size(min = 1)
        Set<Long> grapeVarietiesId,
        @NotNull
        @Min(value = 1)
        Long regionId,
        @DecimalMin("0.0")
        double price,
        @DecimalMin("0.0")
        double alcohol,
        @NotEmpty
        @Min(value = 1)
        int year
) {
}
