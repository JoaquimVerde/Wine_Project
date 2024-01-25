package backend.Wine_Project.converter;

import backend.Wine_Project.dto.shoppingCartDto.CartCreateDto;
import backend.Wine_Project.dto.shoppingCartDto.CartGetDto;
import backend.Wine_Project.model.ShoppingCart;

public class ShoppingCartConverter {

    public static CartGetDto fromModelToCartGetDto(ShoppingCart cart) {
        return new CartGetDto(
                cart.getId(),
                cart.getItems()
        );
    }

    public static ShoppingCart fromCartCreateDtoToModel (CartCreateDto cart) {
        return new ShoppingCart(
                cart.items()
        );
    }
}
