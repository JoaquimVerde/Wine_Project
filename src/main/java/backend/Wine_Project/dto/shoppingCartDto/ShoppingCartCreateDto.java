package backend.Wine_Project.dto.shoppingCartDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record ShoppingCartCreateDto(
        @NotNull
                @Min(value = 1)
        Long clientId,
        @NotNull
                @Size(min = 1)
        Set<Long> itemsId

) {
}
