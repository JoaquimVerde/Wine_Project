package backend.Wine_Project.dto.orderDto;

import jakarta.validation.constraints.*;

public record OrderCreateDto(
        @NotNull
        @NotEmpty
        @Min(value = 1)
        Long clientId,
        @NotNull
        @NotEmpty
        @Min(value = 1)
        Long shoppingCartId
        //@NotEmpty
        //@DecimalMin("0.0")
      //  double totalPrice


) {
}
