package backend.Wine_Project.dto.orderDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record OrderCreateDto(

        @NotNull
        @NotEmpty
        @Min(value = 1)
        Long shoppingCartId

) {
}
