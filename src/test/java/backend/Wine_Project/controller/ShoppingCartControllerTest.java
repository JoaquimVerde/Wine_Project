package backend.Wine_Project.controller;

import backend.Wine_Project.model.Client;
import backend.Wine_Project.model.Item;
import backend.Wine_Project.model.ShoppingCart;
import backend.Wine_Project.model.wine.Wine;
import backend.Wine_Project.repository.ClientRepository;
import backend.Wine_Project.repository.ItemRepository;
import backend.Wine_Project.repository.ShoppingCartRepository;
import backend.Wine_Project.service.shopppingCartService.ShoppingCartServiceImp;
import backend.Wine_Project.util.Messages;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ShoppingCartControllerTest {

    private static ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShoppingCartServiceImp shoppingCartServiceImpMock;
    @Autowired
    private ShoppingCartRepository shoppingCartRepositoryMock;
    @Autowired
    private ItemRepository itemRepositoryMock;
    @Autowired
    private ClientRepository clientRepositoryMock;


    @BeforeAll
    public static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @BeforeEach
    public void init() {
        shoppingCartRepositoryMock.deleteAll();
        shoppingCartRepositoryMock.resetAutoIncrement();
        itemRepositoryMock.deleteAll();
        itemRepositoryMock.resetAutoIncrement();
        clientRepositoryMock.deleteAll();
        clientRepositoryMock.resetAutoIncrement();
    }

    @AfterEach
    public void end() {
        shoppingCartRepositoryMock.deleteAll();
        shoppingCartRepositoryMock.resetAutoIncrement();
        itemRepositoryMock.deleteAll();
        itemRepositoryMock.resetAutoIncrement();
        clientRepositoryMock.deleteAll();
        clientRepositoryMock.resetAutoIncrement();
    }

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Test get shopping carts when no shopping carts on database returns empty list")
    void testGetWinesWhenNoWinesOnDatabaseReturnsEmptyList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/shoppingCarts/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("Test create shopping cart returns status code 201")
    void testCreateShoppingCartReturnsStatusCode201AndId1() throws Exception {

        Client client = new Client();
        Item item = new Item(new Wine(), 5);
        itemRepositoryMock.save(item);
        clientRepositoryMock.save(client);

        String shoppingCartJson = "{\"clientId\": 1, \"itemsId\": [1]}";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/shoppingCarts/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(shoppingCartJson))
                .andExpect(status().isCreated())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();

        Long shoppingCartId = objectMapper.readValue(responseContent, Long.class);
        assertThat(shoppingCartId).isEqualTo(1);
    }

    @Test
    @DisplayName("Test create shopping cart throws exception if already has unordered shopping cart")
    void testCreateShoppingCartWhenAlreadyHasShoppingCartThrowsException() throws Exception {

        Client client = new Client();
        Set<Item> items = new HashSet<>();
        clientRepositoryMock.save(client);
        ShoppingCart shoppingCart = new ShoppingCart(client, items);

        shoppingCartRepositoryMock.save(shoppingCart);

        String shoppingCartJson = "{\"clientId\": 1, \"itemsId\": [2]}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/shoppingCarts/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(shoppingCartJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Messages.ALREADY_HAVE_SHOPPING_CART_TO_ORDER.getMessage()));
    }

    @Test
    @DisplayName("Test create shopping cart with item that does not exist throws exception")
    void testCreateShoppingCartWithItemThatDoesNotExistThrowsException() throws Exception {

        Client client = new Client();

        clientRepositoryMock.save(client);

        String shoppingCartJson = "{\"clientId\": 1, \"itemsId\": [1]}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/shoppingCarts/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(shoppingCartJson))
                .andExpect(status().isNotFound())
                .andExpect(content().string(Messages.ITEM_ID_NOT_FOUND.getMessage() + 1));
    }


}
