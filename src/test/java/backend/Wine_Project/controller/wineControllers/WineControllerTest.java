package backend.Wine_Project.controller.wineControllers;


import backend.Wine_Project.dto.wineDto.WineCreateDto;
import backend.Wine_Project.dto.wineDto.WineUpdateDto;
import backend.Wine_Project.model.wine.GrapeVarieties;
import backend.Wine_Project.model.wine.Region;
import backend.Wine_Project.model.wine.Wine;
import backend.Wine_Project.model.wine.WineType;
import backend.Wine_Project.repository.GrapeVarietiesRepository;
import backend.Wine_Project.repository.RegionRepository;
import backend.Wine_Project.repository.WineRepository;
import backend.Wine_Project.repository.WineTypeRepository;
import backend.Wine_Project.service.wineService.WineServiceImp;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WineControllerTest {

    private static ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WineRepository wineRepository;
    @Autowired
    private WineServiceImp wineServiceImp;
    @Autowired
    private WineTypeRepository wineTypeRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private GrapeVarietiesRepository grapeVarietiesRepository;


    @BeforeAll
    public static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @BeforeEach
    public void init() {
        wineRepository.deleteAll();
        wineRepository.resetAutoIncrement();
        wineTypeRepository.deleteAll();
        wineTypeRepository.resetAutoIncrement();
        regionRepository.deleteAll();
        regionRepository.resetAutoIncrement();
        grapeVarietiesRepository.deleteAll();
        grapeVarietiesRepository.resetAutoIncrement();
    }

    @AfterEach
    public void end() {
        wineRepository.deleteAll();
        wineRepository.resetAutoIncrement();
        wineTypeRepository.deleteAll();
        wineTypeRepository.resetAutoIncrement();
        regionRepository.deleteAll();
        regionRepository.resetAutoIncrement();
        grapeVarietiesRepository.deleteAll();
        grapeVarietiesRepository.resetAutoIncrement();
    }

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Test get wines when no wines on database returns empty list")
    void testGetWinesWhenNoWinesOnDatabaseReturnsEmptyList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/wines/0/5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("Test create a wine when wine returns status code 201")
    void testCreateWineReturnCreateAndGetIdEqualsTo1() throws Exception {

        //Given
        String regionJson = "{\"name\": \"Alentejo\"}";
        String grapeVarietyJson = "{\"name\": \"Touriga\"}";
        String wineTypeJson = "{\"name\": \"Branco\"}";
        String wineJson = "{\"name\": \"Papa Figos\", \"wineTypeId\": 1, \"grapeVarietiesId\": [1], \"regionId\": 1, \"price\": \"7\", \"alcohol\": 11.5, \"year\": 2020}";

        MvcResult resultRegion = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/regions/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(regionJson))
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult resultGrapeVariety = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/grapeVarieties/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(grapeVarietyJson))
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult resultWineType = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/wineTypes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(wineTypeJson))
                .andExpect(status().isCreated())
                .andReturn();


        //When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/wines/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(wineJson))
                .andExpect(status().isCreated())
                .andReturn();

        //Then
        String responseContent = result.getResponse().getContentAsString();

        WineCreateDto wine = objectMapper.readValue(responseContent, WineCreateDto.class);

        //assert client id and name using matchers
        assertThat(wine.name()).isEqualTo("Papa Figos");
        assertThat(wine.wineTypeId()).isEqualTo(1);
        assertThat(wine.regionId()).isEqualTo(1);
        assertThat(wine.year()).isEqualTo(2020);
        assertThat(wine.alcohol()).isEqualTo(11.5);

    }

    @Test
    @DisplayName("Test create wine that already exists in the database")
    void testCreateWineAlreadyExists() throws Exception {
        // Given a wine that already exists in the database
        Set<Long> grapes = new HashSet<>();
        Set<GrapeVarieties> grapesList = new HashSet<>();
        WineType wineType = new WineType("wineType1");
        Region region = new Region("region1");
        regionRepository.save(region);
        wineTypeRepository.save(wineType);
        WineCreateDto existingWine = new WineCreateDto("ExistingWine", 1L, grapes, 1L, 7.99, 12, 2020);

        wineRepository.save(new Wine(existingWine.name(), wineType, region, existingWine.price(), existingWine.alcohol(), existingWine.year(), grapesList));

        // When trying to create the same wine again
        String jsonRequest = objectMapper.writeValueAsString(existingWine);
        mockMvc.perform(post("/api/v1/wines/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isConflict())
                .andExpect(content().string(Messages.WINE_ALREADY_EXISTS.getMessage()));
    }

    @Test
    @DisplayName("Test create wine with future year throws exception")
    void testCreateWineWithFutureYear() throws Exception {

        Set<Long> grapes = new HashSet<>();
        regionRepository.save(new Region("region"));
        wineTypeRepository.save(new WineType("wineType"));
        WineCreateDto wine = new WineCreateDto("Wine", 1L, grapes, 1L, 7.99, 12, 2028);


        // When trying to create wine with future year
        String jsonRequest = objectMapper.writeValueAsString(wine);
        mockMvc.perform(post("/api/v1/wines/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Messages.YEAR_CANNOT_BE_FUTURE.getMessage()));
    }

    @Test
    @DisplayName("Test delete wine returns status code 200")
    void testDeleteWine() throws Exception {

        //Given wine in database
        Set<Long> grapes = new HashSet<>();
        Set<GrapeVarieties> grapesList = new HashSet<>();
        WineType wineType = new WineType("wineType1");
        Region region = new Region("region1");
        regionRepository.save(region);
        wineTypeRepository.save(wineType);

        WineCreateDto existingWine = new WineCreateDto("ExistingWine", 1L, grapes, 1L, 7.99, 12, 2020);

        wineRepository.save(new Wine(existingWine.name(), wineType, region, existingWine.price(), existingWine.alcohol(), existingWine.year(), grapesList));


        // When trying to delete wine
        mockMvc.perform(delete("/api/v1/wines/1"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("Test delete wine that does not exist throws exception")
    void testDeleteWineThatDoesNotExist() throws Exception {


        // When trying to delete wine with wrong id: 2
        mockMvc.perform(delete("/api/v1/wines/2"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(Messages.WINE_ID_NOT_FOUND.getMessage() + 2));

    }

    @Test
    @DisplayName("Test update wine that does not exist throws exception")
    void testUpdateWineThatDoesNotExist() throws Exception {

        Set<Long> grapes = new HashSet<>();

        WineUpdateDto wine = new WineUpdateDto("Wine", 1L, grapes, 1L, 7.99, 12, 2020);


        // When trying to update wine with wrong id: 2
        String jsonRequest = objectMapper.writeValueAsString(wine);
        mockMvc.perform(patch("/api/v1/wines/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isNotFound())
                .andExpect(content().string(Messages.WINE_ID_NOT_FOUND.getMessage() + 2));

    }

    @Test
    @DisplayName("Test update wine with future year")
    void testUpdateWineWithFutureYear() throws Exception {

        //Given saved wine
        Set<Long> grapes = new HashSet<>();
        Set<GrapeVarieties> grapesList = new HashSet<>();
        Region region = new Region("region");
        WineType wineType = new WineType("wineType");
        regionRepository.save(region);
        wineTypeRepository.save(wineType);
        WineUpdateDto wineUpdateDto = new WineUpdateDto("Wine", 1L, grapes, 1L, 7.99, 12, 2028);
        Wine wine = new Wine(wineUpdateDto.name(), wineType, region, wineUpdateDto.price(), wineUpdateDto.alcohol(), 2020, grapesList);

        wineRepository.save(wine);


        // When trying to update wine with future year
        String jsonRequest = objectMapper.writeValueAsString(wineUpdateDto);
        mockMvc.perform(patch("/api/v1/wines/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Messages.YEAR_CANNOT_BE_FUTURE.getMessage()));

    }

    @Test
    @DisplayName("Test search wine by wrong name throws exception")
    void testSearchWineByNameThatDoesNotExist() throws Exception {

        Set<Long> grapes = new HashSet<>();
        Set<GrapeVarieties> grapesList = new HashSet<>();
        WineType wineType = new WineType("wineType1");
        Region region = new Region("region1");
        regionRepository.save(region);
        wineTypeRepository.save(wineType);

        WineCreateDto existingWine = new WineCreateDto("ExistingWine", 1L, grapes, 1L, 7.99, 12, 2020);

        wineRepository.save(new Wine(existingWine.name(), wineType, region, existingWine.price(), existingWine.alcohol(), existingWine.year(), grapesList));


        // When trying to search wine with name that does not exist
        mockMvc.perform(get("/api/v1/wines/search?name=otherName&year=0"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(Messages.WINES_NOT_FOUND.getMessage()));

    }

    @Test
    @DisplayName("Test search wine by negative year throws exception")
    void testSearchWineByYearThatDoesNotExist() throws Exception {

        Set<Long> grapes = new HashSet<>();
        Set<GrapeVarieties> grapesList = new HashSet<>();
        WineType wineType = new WineType("wineType1");
        Region region = new Region("region1");
        regionRepository.save(region);
        wineTypeRepository.save(wineType);

        WineCreateDto existingWine = new WineCreateDto("ExistingWine", 1L, grapes, 1L, 7.99, 12, 2020);

        wineRepository.save(new Wine(existingWine.name(), wineType, region, existingWine.price(), existingWine.alcohol(), existingWine.year(), grapesList));


        // When trying to search wine with negative year
        mockMvc.perform(get("/api/v1/wines/search?year=-2"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(Messages.WINES_NOT_FOUND.getMessage()));

    }

    @Test
    @DisplayName("Test delete wine that was rated throws exception and 409")
    void testDeleteWineThatWasRated() throws Exception {

        //Given wine in DB that was already rated
        Set<GrapeVarieties> grapesList = new HashSet<>();
        WineType wineType = new WineType("wineType1");
        Region region = new Region("region1");
        regionRepository.save(region);
        wineTypeRepository.save(wineType);
        Wine wine = new Wine("wine", wineType, region, 7.99, 12, 2020, grapesList);
        wine.setRated(true);
        wineRepository.save(wine);


        // When trying to delete wine that was rated
        mockMvc.perform(delete("/api/v1/wines/1"))
                .andExpect(status().isConflict())
                .andExpect(content().string(Messages.WINE_WAS_ORDERED_OR_RATED.getMessage() + 1));

    }

    @Test
    @DisplayName("Test delete wine that was ordered throws exception and 409")
    void testDeleteWineThatWasOrdered() throws Exception {

        //Given wine in DB that was already ordered
        Set<GrapeVarieties> grapesList = new HashSet<>();
        WineType wineType = new WineType("wineType1");
        Region region = new Region("region1");
        regionRepository.save(region);
        wineTypeRepository.save(wineType);
        Wine wine = new Wine("wine", wineType, region, 7.99, 12, 2020, grapesList);
        wine.setItem(true);
        wineRepository.save(wine);


        // When trying to delete wine that was ordered
        mockMvc.perform(delete("/api/v1/wines/1"))
                .andExpect(status().isConflict())
                .andExpect(content().string(Messages.WINE_WAS_ORDERED_OR_RATED.getMessage() + 1));

    }


}
