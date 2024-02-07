package backend.Wine_Project.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;


import backend.Wine_Project.exceptions.ShoppingCartAlreadyBeenOrderedException;
import backend.Wine_Project.exceptions.notFound.PdfNotFoundException;
import backend.Wine_Project.exceptions.notFound.ShoppingCartNotFoundException;
import backend.Wine_Project.model.Client;
import backend.Wine_Project.model.Item;
import backend.Wine_Project.model.ShoppingCart;
import backend.Wine_Project.model.wine.GrapeVarieties;
import backend.Wine_Project.model.wine.Region;
import backend.Wine_Project.model.wine.Wine;
import backend.Wine_Project.model.wine.WineType;
import backend.Wine_Project.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.NestedServletException;

import java.io.FileOutputStream;
import java.time.Instant;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
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
    private WineTypeRepository wineTypeRepository;
    @Autowired
    private WineRepository wineRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private GrapeVarietiesRepository grapeVarietiesRepository;
    @Autowired
    private OrderRepository orderRepository;

    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());


    }

    @BeforeEach
    void ini() throws IOException {
        orderRepository.deleteAll();
        deleteInvoices();
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
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setOrdered(false);
        List<Item> findAllItem = itemRepository.findAll();

        shoppingCart.setItems(Collections.emptySet());
        List<Client> allClients = clientRepository.findAll();


        shoppingCart.setClient(allClients.getFirst());
        shoppingCartRepository.save(shoppingCart);
        Long shoppingCartId = shoppingCart.getId();

        String orderJson = "{\"shoppingCartId\":" + shoppingCartId + "}";
        mockMvc.perform(post("/api/v1/wine_orders/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Test get order with invalid ID")
    void testGetOrderWithInvalidId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/wine_orders/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test get order with non-existing ID")
    void testGetOrderWithNonExistingId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/wine_orders/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test create order with non-existing shopping cart ID")
    void testCreateOrderWithNonExistingShoppingCartId() {
        String orderJson = "{\"shoppingCartId\":9999}";
        Exception exception = assertThrows(ServletException.class, () -> {
            mockMvc.perform(post("/api/v1/wine_orders/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(orderJson));
        });
        assertTrue(exception.getCause() instanceof ShoppingCartNotFoundException);
    }

    @Test
    @DisplayName("Test create order with shopping cart ID that has already been ordered")
    void testCreateOrderWithShoppingCartIdThatHasBeenOrdered() throws Exception {
        String orderJson = "{\"shoppingCartId\":1}";
        mockMvc.perform(post("/api/v1/wine_orders/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(status().isConflict());
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
    @ValueSource(strings = {

            "{\"shoppingCartId\": \"invalid_id\"}"
    })
    void testCreateOrderWithInvalidRequestContent(String invalidJsonRequest) throws Exception {
        mockMvc.perform(post("/api/v1/wine_orders/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJsonRequest))
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("Test create an order and returns a status code 201")
    void testCreateOrderAndReturnsStatus201() throws Exception {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setOrdered(false);

        Client client = new Client();
        client.setName("Test Client");

        String uniqueSuffix = String.valueOf(Instant.now().toEpochMilli());
        client.setEmail("unique-email-" + uniqueSuffix + "@example.com");
        client.setNif(Integer.parseInt(uniqueSuffix.substring(uniqueSuffix.length() - 9))); // Ensure the NIF is unique and has 9 digits

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
    @DisplayName("Test create order throws exception")
    void testCreateOrder_ThrowShoppingCartAlreadyBeenOrderedException() throws Exception {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setOrdered(true);
        List<Item> findAllItem = itemRepository.findAll();

        shoppingCart.setItems(Collections.emptySet());
        List<Client> allClients = clientRepository.findAll();


        shoppingCart.setClient(allClients.getFirst());
        shoppingCartRepository.save(shoppingCart);
        Long shoppingCartId = shoppingCart.getId();

        String orderJson = "{\"shoppingCartId\":" + shoppingCartId + "}";
        mockMvc.perform(post("/api/v1/wine_orders/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(status().isConflict())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ShoppingCartAlreadyBeenOrderedException))
                .andExpect(result -> assertEquals("This Shopping cart has already been ordered!", result.getResolvedException().getMessage()));
    }


    @Test
    @DisplayName("test get all orders when 2 orders in database")
    void testGetAllOrdersWhen2OrdersInDatabase() throws Exception {
        //Create 2 Orders
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setOrdered(false);
        List<Item> findAllItem = itemRepository.findAll();

        shoppingCart.setItems(Collections.emptySet());
        List<Client> allClients = clientRepository.findAll();

        shoppingCart.setClient(allClients.getFirst());
        ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);
        Long shoppingCartId = savedShoppingCart.getId();

        String orderJson = "{\"shoppingCartId\":" + shoppingCartId + "}";


        mockMvc.perform(post("/api/v1/wine_orders/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderJson));

        ShoppingCart shoppingCart1 = new ShoppingCart();
        shoppingCart1.setOrdered(false);

        shoppingCart1.setItems(Collections.emptySet());

        shoppingCart1.setClient(allClients.getFirst());
        ShoppingCart savedShoppingCart1 = shoppingCartRepository.save(shoppingCart1);
        Long shoppingCartId1 = savedShoppingCart1.getId();

        String orderJson1 = "{\"shoppingCartId\":" + shoppingCartId1 + "}";


        mockMvc.perform(post("/api/v1/wine_orders/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderJson1));

        // get all orders
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/wine_orders/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));

    }


    private void deleteInvoices() throws IOException {
        Path dir = Paths.get("src/main/java/backend/Wine_Project/invoices");
        Files.walk(dir).sorted(Comparator.reverseOrder()).forEach(path -> {
            try {
                if (!path.equals(dir)) {
                    Files.delete(path);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    private void createInvoiceDirectory() throws IOException {
        Path dir = Paths.get("src/main/java/backend/Wine_Project/invoices");
        Files.createDirectory(dir);

    }

    private void deleteInvoiceDirectory() throws IOException {
        Path dir = Paths.get("src/main/java/backend/Wine_Project/invoices");
        Files.delete(dir);

    }

    @Test
    @DisplayName("Test create order throws Pdf not found")
    void testCreateOrder_ThrowFileNotFoundException() throws Exception {
        deleteInvoiceDirectory();

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setOrdered(false);
        List<Item> findAllItem = itemRepository.findAll();

        shoppingCart.setItems(Collections.emptySet());
        List<Client> allClients = clientRepository.findAll();


        shoppingCart.setClient(allClients.getFirst());
        shoppingCartRepository.save(shoppingCart);
        Long shoppingCartId = shoppingCart.getId();

        String orderJson = "{\"shoppingCartId\":" + shoppingCartId + "}";
        mockMvc.perform(post("/api/v1/wine_orders/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(result -> assertInstanceOf(PdfNotFoundException.class, result.getResolvedException()));
        createInvoiceDirectory();
    }


}