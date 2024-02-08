package backend.Wine_Project.service.shopppingCartService;

import backend.Wine_Project.converter.ShoppingCartConverter;
import backend.Wine_Project.dto.itemDto.ItemCreateDto;
import backend.Wine_Project.dto.shoppingCartDto.ShoppingCartCreateDto;
import backend.Wine_Project.dto.shoppingCartDto.ShoppingCartGetDto;
import backend.Wine_Project.dto.shoppingCartDto.ShoppingCartUpdateDto;
import backend.Wine_Project.exceptions.ShoppingCartAlreadyBeenOrderedException;
import backend.Wine_Project.exceptions.ShoppingCartCannotBeDeletedException;
import backend.Wine_Project.exceptions.ShoppingCartCannotBeUpdatedException;
import backend.Wine_Project.exceptions.alreadyExists.AlreadyHaveShoppingCartToOrderException;
import backend.Wine_Project.exceptions.notFound.ShoppingCartNotFoundException;
import backend.Wine_Project.model.Client;
import backend.Wine_Project.model.Item;
import backend.Wine_Project.model.ShoppingCart;
import backend.Wine_Project.repository.ShoppingCartRepository;
import backend.Wine_Project.service.clientService.ClientService;
import backend.Wine_Project.service.itemService.ItemService;
import backend.Wine_Project.service.wineService.WineService;
import backend.Wine_Project.util.Messages;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ShoppingCartServiceImp implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final WineService wineService;
    private final ClientService clientService;
    private final ItemService itemService;

    public ShoppingCartServiceImp(ShoppingCartRepository shoppingCartRepository, WineService wineService, ClientService clientService, ItemService itemService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.wineService = wineService;
        this.clientService = clientService;
        this.itemService = itemService;
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

        Client client = clientService.getById(cart.clientId());
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findByClientAndOrdered(client, false);
        if (optionalShoppingCart.isPresent()) {
            throw new AlreadyHaveShoppingCartToOrderException(Messages.ALREADY_HAVE_SHOPPING_CART_TO_ORDER.getMessage());
        }
        Set<Item> items = new HashSet<>();
        for (Long itemId : cart.itemsId()) {
            items.add(itemService.getById(itemId));
        }

        ShoppingCart newShoppingCart = new ShoppingCart(client, items);

        shoppingCartRepository.save(newShoppingCart);

        return newShoppingCart.getId();
    }

    @Override
    public void delete(Long id) {

        Optional<ShoppingCart> shCartOptional = shoppingCartRepository.findById(id);

        if (shCartOptional.isEmpty()) {
            throw new ShoppingCartNotFoundException(Messages.SHOPPING_CART_NOT_FOUND.getMessage());
        }
        if (shCartOptional.get().isOrdered()) {
            throw new ShoppingCartCannotBeDeletedException(Messages.SHOPPING_CART_CANNOT_BE_DELETE.getMessage());
        }

        shoppingCartRepository.deleteById(id);
    }

    @Override
    public Long update(Long id, ShoppingCartUpdateDto cartUpdateDto) {

        Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findById(id);

        if (!shoppingCartOptional.isPresent()) {
            throw new ShoppingCartNotFoundException(Messages.SHOPPING_CART_NOT_FOUND.getMessage());
        }

        ShoppingCart shoppingCartToUpdate = shoppingCartOptional.get();

        if (shoppingCartToUpdate.isOrdered()) {
            throw new ShoppingCartCannotBeUpdatedException(Messages.SHOPPING_CART_CANNOT_BE_UPDATED.getMessage());
        }

        if (!shoppingCartToUpdate.getItems().stream().map(Item::getId).equals(cartUpdateDto.itemsId())) {

            Set<Item> itemsToUpdate = new HashSet<>();

            for (Long itemId : cartUpdateDto.itemsId()) {
                itemsToUpdate.add(itemService.getById(itemId));
            }
            shoppingCartToUpdate.setItems(itemsToUpdate);

            shoppingCartToUpdate.setTotalAmount(0);

            for (Item item : shoppingCartToUpdate.getItems()) {

                shoppingCartToUpdate.setTotalAmount(shoppingCartToUpdate.getTotalAmount() + item.getTotalPrice());
            }

            shoppingCartRepository.save(shoppingCartToUpdate);
        }
        return shoppingCartToUpdate.getId();
    }

    @Override
    public Long addItemToShoppingCart(ItemCreateDto item, Long shoppingCartId) {

        Item itemToAdd = new Item(wineService.getById(item.wineId()), item.quantity());
        ShoppingCart shoppingCart = getById(shoppingCartId);

        shoppingCart.getItems().add(itemToAdd);
        shoppingCartRepository.save(shoppingCart);

        return shoppingCartId;
    }


    public void closeShoppingCart(ShoppingCart shoppingCart) {

        if (shoppingCart.isOrdered()) {
            throw new ShoppingCartAlreadyBeenOrderedException(Messages.SHOPPING_CART_ALREADY_ORDERED.getMessage());
        }

        shoppingCart.setOrdered(true);
        shoppingCartRepository.save(shoppingCart);

    }


    @Override
    public ShoppingCart getById(Long id) {
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findById(id);
        if (optionalShoppingCart.isEmpty()) {
            throw new ShoppingCartNotFoundException(Messages.SHOPPING_CART_NOT_FOUND.getMessage());
        }
        return optionalShoppingCart.get();
    }

}
