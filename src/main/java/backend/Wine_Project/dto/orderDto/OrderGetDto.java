package backend.Wine_Project.dto.orderDto;

import backend.Wine_Project.dto.shoppingCartDto.ShoppingCartGetDto;

public record OrderGetDto(
        ShoppingCartGetDto shoppingCart,
        double totalPrice
) {
}
