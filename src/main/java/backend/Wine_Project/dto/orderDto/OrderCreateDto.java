package backend.Wine_Project.dto.orderDto;

import jakarta.validation.constraints.*;

public record OrderCreateDto(

        @NotNull
        @NotEmpty
        @Min(value = 1)
        Long shoppingCartId



) {
}
