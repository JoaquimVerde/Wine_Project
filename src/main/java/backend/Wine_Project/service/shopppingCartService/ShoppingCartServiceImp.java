package backend.Wine_Project.service.shopppingCartService;

import backend.Wine_Project.converter.ShoppingCartConverter;
import backend.Wine_Project.dto.itemDto.ItemCreateDto;
import backend.Wine_Project.dto.shoppingCartDto.ShoppingCartCreateDto;
import backend.Wine_Project.dto.shoppingCartDto.ShoppingCartGetDto;

import backend.Wine_Project.model.Client;

import backend.Wine_Project.exceptions.ShoppingCartCannotBeDeletedException;
import backend.Wine_Project.exceptions.ShoppingCartNotFoundException;

import backend.Wine_Project.model.Item;
import backend.Wine_Project.model.ShoppingCart;
import backend.Wine_Project.repository.ShoppingCartRepository;
import backend.Wine_Project.service.clientService.ClientService;
import backend.Wine_Project.service.itemService.ItemService;
import backend.Wine_Project.service.wineService.WineService;
import backend.Wine_Project.util.Messages;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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
        Set<Item> items = new HashSet<>();
        for (Long itemId: cart.itemsId()) {
            items.add(itemService.getById(itemId));
        }

        ShoppingCart newShoppingCart = new ShoppingCart(client, items);

        shoppingCartRepository.save(newShoppingCart);

        return newShoppingCart.getId();
    }

    @Override
    public void delete(Long id) {

        ShoppingCart shCart = shoppingCartRepository.findById(id).get();

        if (!shoppingCartRepository.findById(id).isPresent()) {
            throw new ShoppingCartNotFoundException(Messages.SHOPPING_CART_NOT_FOUND.getMessage());
        }
        if (shoppingCartRepository.findById(id).isPresent() && shCart.isOrdered()) {
            throw new ShoppingCartCannotBeDeletedException(Messages.SHOPPING_CART_CANNOT_BE_DELETE.getMessage());
        }

        shoppingCartRepository.deleteById(id);
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


    public Long removeItemFromCart(Item item, ShoppingCart shoppingCart) {

        Set<Item> itemsShoppingCart = shoppingCart.getItems();

        for (Item item2 : itemsShoppingCart) {
            if (item2.getWine().equals(item.getWine())) {
                itemsShoppingCart.remove(item);
                break;
            }
        }
        return item.getId();
    }

    public double setTotalAmount(Set<Item> itemsInShoppingCart, ShoppingCart shoppingCart) {

        Set<Item> itemsShoppingCart = shoppingCart.getItems();
        Iterator<Item> it = itemsShoppingCart.iterator();
        double totalAmount = shoppingCart.getTotalAmount();
        while(it.hasNext()) {
            Item nextItem = it.next();
            nextItem.getTotalPrice();
            totalAmount += nextItem.getTotalPrice();
        }

        return totalAmount;
    }

    public void printInvoice(ShoppingCart shoppingCart) {
        Set<Item> itemsShoppingCart = shoppingCart.getItems();
        Iterator<Item> it = itemsShoppingCart.iterator();

        //TODO get another format to the invoice???? email? file?

        while(it.hasNext()) {
            Item nextItem = it.next();

            System.out.println(nextItem.getWine().getName() + "\t");
            System.out.println(nextItem.getQuantity() + "\t");
            System.out.println(nextItem.getTotalPrice() + "\t");
        }
        System.out.println("\t\t\t" + setTotalAmount(shoppingCart.getItems(), shoppingCart));
    }

}
