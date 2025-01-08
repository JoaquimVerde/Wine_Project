package backend.Wine_Project.controller.wineControllers;

import backend.Wine_Project.dto.wineDto.WineReadDto;
import backend.Wine_Project.dto.wineTypeDto.WineTypeCreateDto;
import backend.Wine_Project.model.wine.WineType;
import backend.Wine_Project.repository.WineTypeRepository;
import backend.Wine_Project.util.Messages;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class WineTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WineTypeRepository wineTypeRepository;

    private static ObjectMapper objectMapper;

    @BeforeAll
    public static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @BeforeEach
    void ini() {
        wineTypeRepository.deleteAll();
        wineTypeRepository.resetAutoIncrement();
    }

    @Test
    @DisplayName("Test get all wine types when no wine types in the database")
    void testGetAllWineTypesWhenNoWineTypesInDatabase() throws Exception {
        mockMvc.perform(get("/api/v1/wineTypes/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @DisplayName("Test create a wine type and return status code 201")
    void testCreateWineTypeAndReturnStatus201() throws Exception {
        WineTypeCreateDto wineTypeCreateDto = new WineTypeCreateDto("Red Wine");
        String jsonRequest = objectMapper.writeValueAsString(wineTypeCreateDto);

        mockMvc.perform(post("/api/v1/wineTypes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().string(Messages.WINE_TYPE_CREATED.getMessage()));

        List<WineType> wineTypes = wineTypeRepository.findAll();
        assertThat(wineTypes).hasSize(1);
        assertThat(wineTypes.getFirst().getName()).isEqualTo("Red Wine");
    }

    @Test
    @DisplayName("Test get wines by wine type id")
    void testGetWinesByType() throws Exception {
        WineType wineType = new WineType("Red Wine");
        wineTypeRepository.save(wineType);

        Long wineTypeId = wineType.getId();
        Set<WineReadDto> wines = new HashSet<>(); // Set your expected wines

        mockMvc.perform(get("/api/v1/wineTypes/{wineTypeId}", wineTypeId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty()); // Adjust this based on your expected response
    }

    @Test
    @DisplayName("Test create a wine type with invalid request content")
    void testCreateWineTypeWithInvalidRequestContent() throws Exception {
        // Given an invalid JSON request
        String invalidJsonRequest = "{\"invalid_field\": \"Invalid Wine Type\"}";

        // When trying to create a wine type with invalid content
        mockMvc.perform(post("/api/v1/wineTypes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test create a wine type that already exists in the database")
    void testCreateWineTypeAlreadyExists() throws Exception {
        WineType existingWineType = new WineType("Red Wine");
        wineTypeRepository.save(existingWineType);

        WineTypeCreateDto existingWineTypeDto = new WineTypeCreateDto("Red Wine");

        String jsonRequest = objectMapper.writeValueAsString(existingWineTypeDto);
        mockMvc.perform(post("/api/v1/wineTypes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isConflict())
                .andExpect(content().string(Messages.WINE_TYPE_ALREADY_EXISTS.getMessage()));
    }

    @Test
    @DisplayName("Test create multiple wine types")
    void testCreateMultipleWineTypes() throws Exception {
        List<WineTypeCreateDto> wineTypes = List.of(
                new WineTypeCreateDto("Red Wine"),
                new WineTypeCreateDto("White Wine")
        );
        String jsonRequest = objectMapper.writeValueAsString(wineTypes);

        mockMvc.perform(post("/api/v1/wineTypes/addWineTypes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].name").value("Red Wine"))
                .andExpect(jsonPath("$.[1].name").value("White Wine"));

        List<WineType> wineTypeList = wineTypeRepository.findAll();
        assertThat(wineTypeList).hasSize(2);
    }

    @Test
    @DisplayName("Test create an empty wineType")
    void testCreateEmptyWineType() throws Exception {
        // Given a JSON request with empty name
        String jsonRequest = "{\"name\": \"\"}";

        // When trying to create a wine type with empty name
        mockMvc.perform(post("/api/v1/wineTypes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test update a wineType status code 200")
    void testUpdateWineType() throws Exception {

        WineType wineType = new WineType();
        wineTypeRepository.save(wineType);
        String jsonUpdateRequest = "{\"name\": \"Branco\"}";

        mockMvc.perform(patch("/api/v1/wineTypes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdateRequest))
                .andExpect(status().isOk())
                .andExpect(content().string("WineType successfully updated"));
    }

    @Test
    @DisplayName("Test update a WineType that does not exist")
    void testUpdateWineTypeThatDoesNotExist() throws Exception {

        String jsonUpdateRequest = "{\"name\": \"Branco\"}";

        // When trying to update a grape Variety that does not exist
        mockMvc.perform(patch("/api/v1/wineTypes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdateRequest))
                .andExpect(status().isNotFound())
                .andExpect(content().string(Messages.WINE_TYPE_ID_NOT_FOUND.getMessage()));
    }

    @Test
    @DisplayName("Test update a WineType with empty throws 400")
    void testUpdateWineTypeWithEmpty() throws Exception {

        WineType wineType = new WineType("Branco");
        wineTypeRepository.save(wineType);

        String jsonUpdateRequest = "{\"name\": \"\"}";

        mockMvc.perform(patch("/api/v1/wineTypes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdateRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"name\":\"Insert a valid wineType name\"}"));
    }
}