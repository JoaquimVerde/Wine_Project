package backend.Wine_Project.converter;

import backend.Wine_Project.dto.shoppingCartDto.ShoppingCartCreateDto;
import backend.Wine_Project.dto.shoppingCartDto.ShoppingCartGetDto;
import backend.Wine_Project.model.ShoppingCart;

public class ShoppingCartConverter {

    public static ShoppingCartGetDto fromModelToCartGetDto(ShoppingCart cart) {
        return new ShoppingCartGetDto(
                cart.getId(),
                cart.getItems()
        );
    }



}
