package backend.Wine_Project.service.shopppingCartService;

import backend.Wine_Project.dto.itemDto.ItemCreateDto;
import backend.Wine_Project.dto.shoppingCartDto.CartCreateDto;
import backend.Wine_Project.dto.shoppingCartDto.CartGetDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImp implements ShoppingCart{
    @Override
    public List<CartGetDto> getAll() {
        return null;
    }

    @Override
    public Long create(CartCreateDto modelCreateDto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(Long id, CartGetDto modelUpdateDto) {

    }

    @Override
    public CartGetDto get(Long id) {
        return null;
    }

    public void addItemToCart(ItemCreateDto item) {

    }




}
