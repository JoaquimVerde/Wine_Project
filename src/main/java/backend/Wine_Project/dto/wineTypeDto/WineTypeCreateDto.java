package backend.Wine_Project.dto.wineTypeDto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WineTypeCreateDto(
        @NotNull
        @NotBlank
        String name
) {
}
