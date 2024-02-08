package backend.Wine_Project.controller;

import java.time.Instant;

import backend.Wine_Project.model.Client;
import backend.Wine_Project.model.Item;
import backend.Wine_Project.model.Order;
import backend.Wine_Project.model.ShoppingCart;
import backend.Wine_Project.repository.*;
import backend.Wine_Project.util.Messages;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static ObjectMapper objectMapper;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private OrderRepository orderRepository;


    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());


    }

    @BeforeEach
    void ini() {
        orderRepository.deleteAll();
        orderRepository.resetAutoIncrement();
        shoppingCartRepository.deleteAll();
        shoppingCartRepository.resetAutoIncrement();
        clientRepository.deleteAll();
        clientRepository.resetAutoIncrement();

    }

    @AfterEach
    void end() {
        orderRepository.deleteAll();
        orderRepository.resetAutoIncrement();
        shoppingCartRepository.deleteAll();
        shoppingCartRepository.resetAutoIncrement();
        clientRepository.deleteAll();
        clientRepository.resetAutoIncrement();

    }

    @Test
    @DisplayName("Test get all orders")
    void testGetAllOrders() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/wine_orders/"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test create order")
    void testCreateOrder() throws Exception {

        Set<Item> items = new HashSet<>();
        Client client = new Client();
        clientRepository.save(client);
        ShoppingCart shoppingCart = new ShoppingCart(client,items);
        shoppingCartRepository.save(shoppingCart);


        String orderJson = "{\"shoppingCartId\": 1 }";
        mockMvc.perform(post("/api/v1/wine_orders/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(status().isCreated());
    }



    @Test
    @DisplayName("Test create order with non-existing shopping cart ID")
    void testCreateOrderWithNonExistingShoppingCartId() throws Exception {

        String orderJson = "{\"shoppingCartId\":9999}";

        mockMvc.perform(post("/api/v1/wine_orders/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(status().isNotFound())
                .andExpect(content().string(Messages.SHOPPING_CART_NOT_FOUND.getMessage()));


    }

    @Test
    @DisplayName("Test create order with shopping cart ID that has already been ordered")
    void testCreateOrderWithShoppingCartIdThatHasBeenOrdered() throws Exception {

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCartRepository.save(shoppingCart);
        Order order = new Order(shoppingCart);
        shoppingCart.setOrdered(true);
        orderRepository.save(order);
        shoppingCartRepository.save(shoppingCart);

        String orderJson = "{\"shoppingCartId\":1}";
        mockMvc.perform(post("/api/v1/wine_orders/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(status().isConflict())
                .andExpect(content().string(Messages.SHOPPING_CART_ALREADY_ORDERED.getMessage()));
    }


    @Test
    @DisplayName("Test get all orders when no orders in database")
    void testGetAllOrdersWhenNoOrdersInDatabase() throws Exception {
        orderRepository.deleteAll();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/wine_orders/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("Test get all orders when no orders on database returns a empty list")
    void testGetAllOrdersWhenNoOrdersOnDatabase() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/wine_orders/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @ParameterizedTest
    @DisplayName("Test create an order with invalid request content")
    @ValueSource(strings = {"{\"shoppingCartId\": \"invalid_id\"}"})
    void testCreateOrderWithInvalidRequestContent(String invalidJsonRequest) throws Exception {
        mockMvc.perform(post("/api/v1/wine_orders/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJsonRequest))
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("Test create an order that sends the email and returns a status code 201")
    void testCreateOrderAndReturnsStatus201() throws Exception {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setOrdered(false);

        Client client = new Client();
        client.setName("Test Client");

        String uniqueSuffix = String.valueOf(Instant.now().toEpochMilli());
        client.setEmail("unique-email-" + uniqueSuffix + "@example.com");
        client.setNif(Integer.parseInt(uniqueSuffix.substring(uniqueSuffix.length() - 9)));

        clientRepository.save(client);
        shoppingCart.setClient(client);

        shoppingCartRepository.save(shoppingCart);
        Long shoppingCartId = shoppingCart.getId();

        String orderJson = "{\"shoppingCartId\":" + shoppingCartId + "}";
        mockMvc.perform(post("/api/v1/wine_orders/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(status().isCreated());
    }



    @Test
    @DisplayName("test get all orders when 2 orders in database")
    void testGetAllOrdersWhen2OrdersInDatabase() throws Exception {
        //Create 2 Orders

        Set<Item> items = new HashSet<>();
        Client client = new Client("joasddas", "asdasd@email.com", 113746533);
        clientRepository.save(client);
        ShoppingCart shoppingCart = new ShoppingCart(client,items);
        shoppingCartRepository.save(shoppingCart);
        Order order = new Order(shoppingCart);
        orderRepository.save(order);

        Set<Item> items2 = new HashSet<>();
        Client client2 = new Client();
        clientRepository.save(client2);
        ShoppingCart shoppingCart2 = new ShoppingCart(client2,items2);
        shoppingCartRepository.save(shoppingCart2);
        Order order2 = new Order(shoppingCart2);
        orderRepository.save(order2);


        // get all orders
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/wine_orders/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));

    }



    @Test
    @DisplayName("Test update order id does not exist throws order not found and 404")
    void testUpdateOrderThatDoesNotExist() throws Exception {

        String orderUpdateJson = "{\"isPaid\":\"true\"}";

        mockMvc.perform(patch("/api/v1/wine_orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderUpdateJson))
                .andExpect(status().isNotFound())
                .andExpect(content().string(Messages.ORDER_ID_NOT_FOUND.getMessage()));
    }

    @Test
    @DisplayName("Test update order returns 200 and sends email")
    void testUpdateOrderReturns200AndSendsEmail() throws Exception {

        Set<Item> items = new HashSet<>();
        Client client = new Client("Joaquim Verde","joaquimsacchetti@gmail.com", 116333222 );
        clientRepository.save(client);
        ShoppingCart shoppingCart = new ShoppingCart(client, items);
        shoppingCartRepository.save(shoppingCart);
        Order order = new Order(shoppingCart);
        orderRepository.save(order);

        String orderUpdateJson = "{\"isPaid\":\"true\"}";

        mockMvc.perform(patch("/api/v1/wine_orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderUpdateJson))
                .andExpect(status().isOk());
    }

}