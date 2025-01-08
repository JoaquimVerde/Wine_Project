package backend.Wine_Project.controller;

import backend.Wine_Project.dto.itemDto.ItemCreateDto;
import backend.Wine_Project.model.Item;
import backend.Wine_Project.model.wine.Wine;
import backend.Wine_Project.model.wine.WineType;
import backend.Wine_Project.repository.ItemRepository;
import backend.Wine_Project.repository.WineRepository;
import backend.Wine_Project.repository.WineTypeRepository;
import backend.Wine_Project.util.Messages;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WineRepository wineRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private WineTypeRepository wineTypeRepository;


    private static ObjectMapper objectMapper;
    private Long itemId;

    @BeforeEach
    void setUp() throws Exception {
        objectMapper = new ObjectMapper();
        itemRepository.deleteAll();
        itemRepository.resetAutoIncrement();
        wineRepository.deleteAll();
        wineRepository.resetAutoIncrement();
        wineTypeRepository.deleteAll();
        wineTypeRepository.resetAutoIncrement();

    }


    @Test
    @DisplayName("Test get all items when no items on database returns an empty list")
    void testGetAllItemsWhenNoItemsOnDatabase() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/items/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("Test create an item and returns a status code 201")
    void testCreateItemAndReturnsStatus201() throws Exception {
        WineType testeWine = new WineType("TesteWine");
        wineTypeRepository.save(testeWine);

        Wine wine = new Wine();
        wine.setWineType(testeWine);
        wine.setName("Test Wine");
        wine.setPrice(10.0);
        wine.setAlcohol(12.0);
        wine.setYear(2020);
        Wine savedWine = wineRepository.save(wine);

        ItemCreateDto item = new ItemCreateDto(savedWine.getId(), 10);
        String itemJson = objectMapper.writeValueAsString(item);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/items/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(itemJson))
                .andExpect(status().isCreated())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        itemId = objectMapper.readValue(responseContent, Long.class);
        Assertions.assertNotNull(itemId);
    }

    @Test
    @DisplayName("Test get a single item")
    void testGetSingleItem() throws Exception {
        WineType testeWine = new WineType("TesteWine");
        wineTypeRepository.save(testeWine);

        Wine wine = new Wine();
        wine.setName("Test Wine");
        wine.setPrice(10.0);
        wine.setAlcohol(12.0);
        wine.setYear(2020);

        wine.setWineType(testeWine);
        Wine savedWine = wineRepository.saveAndFlush(wine);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/items/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"wineId\":" + savedWine.getId() + ",\"quantity\":1}"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/items/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @DisplayName("Test create an item already exists throws exception")
    void testCreateItemAlreadyExistsThrowsException() throws Exception {

        Wine wine = new Wine();

        Item item = new Item(wine, 2);
        itemRepository.save(item);

        String itemCreateJson = "{\"wineId\": 1, \"quantity\": 2}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/items//")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(itemCreateJson))
                .andExpect(status().isConflict())
                .andExpect(content().string(Messages.ITEM_ALREADY_EXISTS.getMessage() + 1));

    }

    @Test
    @DisplayName("Test create an item with wine id less than 1 throws status code 400")
    void testCreateItemWithWineIdLessThan1() throws Exception {

        String itemCreateJson = "{\"wineId\": -1, \"quantity\": 2}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/items/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(itemCreateJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Messages.WINE_ID_MUST_NOT_BE_LESS_THAN_1.getMessage()));

    }

    @Test
    @DisplayName("Test create an item with wine id empty throws status code 400")
    void testCreateItemWithWineIdEmpty() throws Exception {

        String itemCreateJson = "{\"wineId\": , \"quantity\": 2}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/items/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(itemCreateJson))
                .andExpect(status().isBadRequest());
    }


}