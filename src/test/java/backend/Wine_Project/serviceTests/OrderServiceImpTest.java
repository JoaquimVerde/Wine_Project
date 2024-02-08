package backend.Wine_Project.serviceTests;

import backend.Wine_Project.dto.orderDto.OrderCreateDto;
import backend.Wine_Project.exceptions.ShoppingCartAlreadyBeenOrderedException;
import backend.Wine_Project.model.Client;
import backend.Wine_Project.model.Item;
import backend.Wine_Project.model.Order;
import backend.Wine_Project.model.ShoppingCart;
import backend.Wine_Project.model.wine.GrapeVarieties;
import backend.Wine_Project.model.wine.Region;
import backend.Wine_Project.model.wine.Wine;
import backend.Wine_Project.model.wine.WineType;
import backend.Wine_Project.repository.OrderRepository;
import backend.Wine_Project.service.EmailService;
import backend.Wine_Project.service.orderService.OrderServiceImp;
import backend.Wine_Project.service.shopppingCartService.ShoppingCartServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class OrderServiceImpTest {


    private OrderServiceImp orderServiceImp;
    @MockBean
    private OrderRepository orderRepositoryMock;
    @MockBean
    private ShoppingCartServiceImp shoppingCartServiceMock;
    @MockBean
    private EmailService emailService;


    @BeforeEach
    public void setUp() {
        orderServiceImp = new OrderServiceImp(orderRepositoryMock, shoppingCartServiceMock, emailService);
    }

    @Test
    void getAllOrdersReturnsExpectedOrders() {
        Region region = new Region("Alentejo");
        WineType wineType = new WineType("Branco");
        GrapeVarieties grapeVarieties = new GrapeVarieties("Touriga");
        Set<GrapeVarieties> grapeVarietiesSet = new HashSet<>();
        grapeVarietiesSet.add(grapeVarieties);
        Wine wine = new Wine("Papa Figos", wineType, region, 7.99, 12, 2020, grapeVarietiesSet);
        Item item1 = new Item(wine, 3);
        Client client = new Client("Joaquim", "jverde@email.com", 112343233);

        Set<Item> items = new HashSet<>();
        items.add(item1);

        Item item2 = new Item(wine, 4);
        Set<Item> items2 = new HashSet<>();
        items2.add(item2);


        ShoppingCart shoppingCart1 = new ShoppingCart(client, items);
        ShoppingCart shoppingCart2 = new ShoppingCart(client, items2);
        Order order1 = new Order(shoppingCart1);
        Order order2 = new Order(shoppingCart2);
        when(orderRepositoryMock.findAll()).thenReturn(Arrays.asList(order1, order2));

        List<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);

        assertEquals(2, orders.size());
    }

    @Test
    void insertSameItemInOrdersThrowsException() {
        Region region = new Region("Alentejo");
        WineType wineType = new WineType("Branco");
        GrapeVarieties grapeVarieties = new GrapeVarieties("Touriga");
        Set<GrapeVarieties> grapeVarietiesSet = new HashSet<>();
        grapeVarietiesSet.add(grapeVarieties);
        Wine wine = new Wine("Papa Figos", wineType, region, 7.99, 12, 2020, grapeVarietiesSet);
        Item item1 = new Item(wine, 3);
        Client client = new Client("Joaquim", "jverde@email.com", 112343233);

        Set<Item> items = new HashSet<>();
        items.add(item1);

        ShoppingCart shoppingCart1 = new ShoppingCart(client, items);
        ShoppingCart shoppingCart2 = new ShoppingCart(client, items);
        Order order1 = new Order(shoppingCart1);
        Order order2 = new Order(shoppingCart2);

        when(orderRepositoryMock.save(order2)).thenThrow(ShoppingCartAlreadyBeenOrderedException.class);

    }


    @Test
    void createOrderThrowsExceptionWhenOrderExists() {
        Client client = new Client();
        client.setEmail("test@test.com");
        ShoppingCart shoppingCart = new ShoppingCart(client, new HashSet<>());
        shoppingCart.setOrdered(true);
        Order order = new Order(shoppingCart);
        order.setId(1L);
        when(orderRepositoryMock.findById(1L)).thenReturn(Optional.of(order));
        when(shoppingCartServiceMock.getById(anyLong())).thenReturn(shoppingCart);

        assertThrows(ShoppingCartAlreadyBeenOrderedException.class, () -> orderServiceImp.create(new OrderCreateDto(1L)));
    }
}