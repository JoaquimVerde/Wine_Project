package backend.Wine_Project.dto.wineDto;


import jakarta.validation.constraints.*;
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
