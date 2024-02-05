package backend.Wine_Project.controller;

import backend.Wine_Project.dto.itemDto.ItemCreateDto;
import backend.Wine_Project.model.wine.Wine;
import backend.Wine_Project.repository.ItemRepository;
import backend.Wine_Project.repository.WineRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private static ObjectMapper objectMapper;
    private Long itemId;

    @BeforeEach
    void setUp() throws Exception {
        objectMapper = new ObjectMapper();

        itemRepository.deleteAll();
        wineRepository.deleteAll();
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
        Wine wine = new Wine();
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

        if (itemId == null) {
            throw new RuntimeException("Item ID is null after creation");
        }
    }

    @Test
    @DisplayName("Test get a single item")
    void testGetSingleItem() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/items/" + itemId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}