package backend.Wine_Project.controller.wineControllers;

import backend.Wine_Project.dto.regionDto.RegionCreateDto;
import backend.Wine_Project.model.wine.Region;
import backend.Wine_Project.repository.RegionRepository;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class RegionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RegionRepository regionRepository;

    private static ObjectMapper objectMapper;

    @BeforeAll
    public static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @BeforeEach
    void ini() {
        regionRepository.deleteAll();
        regionRepository.resetAutoIncrement();
    }

    @Test
    @DisplayName("Test get all regions when no regions in the database")
    void testGetAllRegionsWhenNoRegionsInDatabase() throws Exception {
        mockMvc.perform(get("/api/v1/regions/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @DisplayName("Test create a region and return status code 201")
    void testCreateRegionAndReturnStatus201() throws Exception {
        RegionCreateDto regionCreateDto = new RegionCreateDto("NewRegion");
        String jsonRequest = objectMapper.writeValueAsString(regionCreateDto);

        mockMvc.perform(post("/api/v1/regions/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().string(Messages.REGION_CREATED.getMessage()));

        List<Region> regions = regionRepository.findAll();
        assertThat(regions).hasSize(1);
        assertThat(regions.getFirst().getName()).isEqualTo("NewRegion");
    }

    @Test
    @DisplayName("Test get wines by region id")
    void testGetWinesByRegionId() throws Exception {
        Region region = new Region("Region1");
        regionRepository.save(region);

        Long regionId = region.getId();

        mockMvc.perform(get("/api/v1/regions/{regionId}", regionId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());


    }

    @Test
    @DisplayName("Test create a region with invalid request content")
    void testCreateRegionWithInvalidRequestContent() throws Exception {
        // Given an invalid JSON request
        String invalidJsonRequest = "{\"invalid_field\": \"Invalid Region\"}";

        // When trying to create a region with invalid content
        mockMvc.perform(post("/api/v1/regions/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test create a region that already exists in the database")
    void testCreateRegionAlreadyExists() throws Exception {
        Region existingRegion = new Region("ExistingRegion");
        regionRepository.save(existingRegion);

        RegionCreateDto existingRegionDto = new RegionCreateDto("ExistingRegion");

        String jsonRequest = objectMapper.writeValueAsString(existingRegionDto);
        mockMvc.perform(post("/api/v1/regions/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isConflict())
                .andExpect(content().string(Messages.REGION_ALREADY_EXISTS.getMessage()));
    }

    @Test
    @DisplayName("Test create multiple regions")
    void testCreateMultipleRegions() throws Exception {
        List<RegionCreateDto> regions = List.of(
                new RegionCreateDto("Region1"),
                new RegionCreateDto("Region2")
        );
        String jsonRequest = objectMapper.writeValueAsString(regions);

        mockMvc.perform(post("/api/v1/regions/addRegions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].name").value("Region1"))
                .andExpect(jsonPath("$.[1].name").value("Region2"));

        List<Region> regionEntities = regionRepository.findAll();
        assertThat(regionEntities).hasSize(2);
    }

    @Test
    @DisplayName("Test create a region with empty name")
    void testCreateRegionWithEmptyName() throws Exception {
        // Given a JSON request with empty name
        String jsonRequest = "{\"name\": \"\"}";

        // When trying to create a region with empty name
        mockMvc.perform(post("/api/v1/regions/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test create a region with letter punctuation")
    void testCreateRegionWithELetterPunctuation() throws Exception {
        // Given a JSON request with character punctuation
        String jsonRequest = "{\"name\": \"Península\"}";

        // When trying to create a region with character punctuation
        mockMvc.perform(post("/api/v1/regions/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Test update a region status code 200")
    void testUpdateRegion() throws Exception {

        Region region = new Region();
        regionRepository.save(region);
        String jsonUpdateRequest = "{\"name\": \"Península de Setúbal\"}";

        mockMvc.perform(patch("/api/v1/regions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdateRequest))
                .andExpect(status().isOk())
                .andExpect(content().string("Region successfully updated"));
    }

    @Test
    @DisplayName("Test update a region that does not exist")
    void testUpdateRegionThatDoesNotExist() throws Exception {

        String jsonUpdateRequest = "{\"name\": \"Setúbal\"}";

        // When trying to update a grape Variety that does not exist
        mockMvc.perform(patch("/api/v1/regions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdateRequest))
                .andExpect(status().isNotFound())
                .andExpect(content().string(Messages.REGION_ID_NOT_FOUND.getMessage()));
    }

    @Test
    @DisplayName("Test update a region with empty throws 400")
    void testUpdateRegionWithEmpty() throws Exception {

        Region region = new Region("Península");
        regionRepository.save(region);
        String jsonUpdateRequest = "{\"name\": \"\"}";

        mockMvc.perform(patch("/api/v1/regions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdateRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"name\":\"Insert a valid region name\"}"));
    }
}