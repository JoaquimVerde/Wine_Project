package backend.Wine_Project.controller;

import backend.Wine_Project.model.Client;
import backend.Wine_Project.model.Item;
import backend.Wine_Project.model.ShoppingCart;
import backend.Wine_Project.model.wine.GrapeVarieties;
import backend.Wine_Project.model.wine.Region;
import backend.Wine_Project.model.wine.Wine;
import backend.Wine_Project.model.wine.WineType;
import backend.Wine_Project.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.pdf.PdfWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.FileOutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;
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

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
  //      shoppingCartRepository.deleteAll();
  //      orderRepository.deleteAll();

//        clientRepository.deleteAll();
//        clientRepository.resetAutoIncrement();
//        wineRepository.deleteAll();
//        wineTypeRepository.deleteAll();
//        itemRepository.deleteAll();
//        regionRepository.deleteAll();
//        grapeVarietiesRepository.deleteAll();


    }

    @Test
    void testGetAllOrders() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/wine_orders/"))
                .andExpect(status().isOk());
    }

    @Test
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
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/wine_orders/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(status().isCreated());
    }

}