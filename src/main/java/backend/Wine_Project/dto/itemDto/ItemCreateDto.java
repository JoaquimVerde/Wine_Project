package backend.Wine_Project.dto.itemDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ItemCreateDto(
        @NotNull
        @NotEmpty
        Long wineId,
        @NotEmpty
        @Min(value = 1)
        int quantity
) {
}
