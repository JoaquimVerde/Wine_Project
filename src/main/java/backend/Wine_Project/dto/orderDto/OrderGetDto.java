package backend.Wine_Project.dto.orderDto;

import backend.Wine_Project.dto.clientDto.ClientReadDto;
import backend.Wine_Project.dto.shoppingCartDto.ShoppingCartGetDto;

public record OrderGetDto(

        ClientReadDto client,

        ShoppingCartGetDto shoppingCart,
        double totalPrice
) {
}
