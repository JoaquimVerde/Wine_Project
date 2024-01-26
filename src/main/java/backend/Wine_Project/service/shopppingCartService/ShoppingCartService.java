package backend.Wine_Project.service.shopppingCartService;

import backend.Wine_Project.dto.shoppingCartDto.ShoppingCartCreateDto;
import backend.Wine_Project.dto.shoppingCartDto.ShoppingCartGetDto;
import backend.Wine_Project.service.CrudService;

public interface ShoppingCartService extends CrudService<ShoppingCartGetDto, ShoppingCartCreateDto, Long > {
}
