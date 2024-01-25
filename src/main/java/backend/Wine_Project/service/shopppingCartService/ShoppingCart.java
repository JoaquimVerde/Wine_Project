package backend.Wine_Project.service.shopppingCartService;

import backend.Wine_Project.dto.shoppingCartDto.CartCreateDto;
import backend.Wine_Project.dto.shoppingCartDto.CartGetDto;
import backend.Wine_Project.service.CrudService;

public interface ShoppingCart extends CrudService<CartGetDto, CartCreateDto, Long > {
}
