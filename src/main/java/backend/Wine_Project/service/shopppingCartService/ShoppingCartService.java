package backend.Wine_Project.service.shopppingCartService;

import backend.Wine_Project.dto.itemDto.ItemCreateDto;
import backend.Wine_Project.dto.shoppingCartDto.ShoppingCartCreateDto;
import backend.Wine_Project.dto.shoppingCartDto.ShoppingCartGetDto;
import backend.Wine_Project.dto.shoppingCartDto.ShoppingCartUpdateDto;
import backend.Wine_Project.model.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {


    List<ShoppingCartGetDto> getAll();

    Long create(ShoppingCartCreateDto cart);

    void delete(Long id);


    Long update(Long id, ShoppingCartUpdateDto cartUpdateDto);

    Long addItemToShoppingCart(ItemCreateDto item, Long shoppingCartId);

    ShoppingCart getById(Long id);
}
