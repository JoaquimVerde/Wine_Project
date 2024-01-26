package backend.Wine_Project.service.shopppingCartService;

import backend.Wine_Project.converter.ShoppingCartConverter;
import backend.Wine_Project.dto.itemDto.ItemCreateDto;
import backend.Wine_Project.dto.shoppingCartDto.ShoppingCartCreateDto;
import backend.Wine_Project.dto.shoppingCartDto.ShoppingCartGetDto;
import backend.Wine_Project.model.Item;
import backend.Wine_Project.model.ShoppingCart;
import backend.Wine_Project.repository.ShoppingCartRepository;
import backend.Wine_Project.service.wineService.WineService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ShoppingCartServiceImp implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final WineService wineService;

    public ShoppingCartServiceImp(ShoppingCartRepository shoppingCartRepository, WineService wineService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.wineService = wineService;
    }

    @Override
    public List<ShoppingCartGetDto> getAll() {
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findAll();
        return shoppingCarts.stream()
                .map(ShoppingCartConverter::fromModelToCartGetDto)
                .toList();
    }

    @Override
    public Long create(ShoppingCartCreateDto cart) {

        ShoppingCart shoppingCart = ShoppingCartConverter.fromCartCreateDtoToModel(cart);

        shoppingCartRepository.save(shoppingCart);

        return shoppingCart.getId();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(Long id, ShoppingCartGetDto modelUpdateDto) {

    }

    @Override
    public ShoppingCartGetDto get(Long id) {
        return null;
    }


    public Set<Item> addItemToShoppingCart(ItemCreateDto item, ShoppingCart shoppingCart) {

        Item itemToAdd = new Item(wineService.getById(item.wineId()), item.quantity());
        Set<Item> itemSet = shoppingCart.getItems();
        itemSet.add(itemToAdd);

        return itemSet;
    }

    /*
    public void removeFromCart(Item item) {

        Iterator<Item> it = items.iterator();

        while(it.hasNext()) {
            Item item2 = it.next();
            if (item2.getWine().equals(item.getWine())) {
                this.items.remove(item);
                break;
            }
        }
    }

     */




}
