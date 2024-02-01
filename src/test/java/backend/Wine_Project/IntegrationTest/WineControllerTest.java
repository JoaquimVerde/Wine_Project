package backend.Wine_Project.IntegrationTest;

import backend.Wine_Project.dto.wineDto.WineCreateDto;
import backend.Wine_Project.exceptions.YearCannotBeFutureException;
import backend.Wine_Project.repository.GrapeVarietiesRepository;
import backend.Wine_Project.repository.RegionRepository;
import backend.Wine_Project.repository.WineRepository;
import backend.Wine_Project.repository.WineTypeRepository;
import backend.Wine_Project.service.wineService.WineServiceImp;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        String regionJson ="{\"name\": \"Alentejo\"}";
        String grapeVarietyJson ="{\"name\": \"Touriga\"}";
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


}
