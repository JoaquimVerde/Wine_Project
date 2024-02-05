package backend.Wine_Project.controller.wineControllers;

import backend.Wine_Project.converter.wineConverters.GrapeVarietiesConverter;
import backend.Wine_Project.dto.grapeVarietiesDto.GrapeVarietiesDto;
import backend.Wine_Project.model.wine.GrapeVarieties;
import backend.Wine_Project.repository.GrapeVarietiesRepository;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;


import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GrapeVarietiesControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private GrapeVarietiesRepository grapeVarietiesRepository;
    private static ObjectMapper objectMapper;

    @BeforeAll
    public static void setUp(){
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }
    @BeforeEach
    void ini() {
        grapeVarietiesRepository.deleteAll();
        grapeVarietiesRepository.resetAutoIncrement();
    }

    @Test
    @DisplayName("Test get all grape varieties when no grape varieties on database")
    void testGetAllGrapeVarietiesWhenNoGrapeVarietiesOnDatabase() throws Exception {
        mockMvc.perform(get("/api/v1/grapeVarieties/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",hasSize(0)));
    }
    @Test
    @DisplayName("Test create a grape variety and return status code 201")
    void testCreateGrapeVarietyAndReturnStatus201()throws Exception{
        GrapeVarietiesDto grapeVarietyDto = new GrapeVarietiesDto("Toriga Nacional");
        String jsonRequest = objectMapper.writeValueAsString(grapeVarietyDto);

        // When
        mockMvc.perform(post("/api/v1/grapeVarieties/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().string(Messages.GRAPE_VARIETY_CREATED.getMessage()));

        // Then
        List<GrapeVarieties> grapeVarietiesList = grapeVarietiesRepository.findAll();
        assertThat(grapeVarietiesList).hasSize(1);
        assertThat(grapeVarietiesList.getFirst().getName()).isEqualTo("Toriga Nacional");
    }
    @Test
    @DisplayName("Test get all grape varieties when some grape varieties are present in the database")
    void testGetAllGrapeVarietiesWhenGrapeVarietiesPresent() throws Exception {
        // Given
        GrapeVarietiesDto grape1 = new GrapeVarietiesDto("Grape1");
        GrapeVarietiesDto grape2 = new GrapeVarietiesDto("Grape2");
        grapeVarietiesRepository.saveAll(List.of(GrapeVarietiesConverter.fromGrapeVarietiesDtoToGrapeVarieties(grape1), GrapeVarietiesConverter.fromGrapeVarietiesDtoToGrapeVarieties(grape2)));

        // When
        mockMvc.perform(get("/api/v1/grapeVarieties/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].name").value("Grape1"))
                .andExpect(jsonPath("$.[1].name").value("Grape2"));
    }
    @Test
    @DisplayName("Test create a grape variety with invalid request content")
    void testCreateGrapeVarietyWithInvalidRequestContent() throws Exception {
        // Given an invalid JSON request
        String invalidJsonRequest = "{\"invalid_field\": \"Invalid Grape\"}";

        // When trying to create a grape variety with invalid content
        mockMvc.perform(post("/api/v1/grapeVarieties/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJsonRequest))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("Test create a grape variety that already exists in the database")
    void testCreateGrapeVarietyAlreadyExists() throws Exception {
        // Given a grape variety already existing in the database
        GrapeVarietiesDto existingGrape = new GrapeVarietiesDto("ExistingGrape");
        grapeVarietiesRepository.save(GrapeVarietiesConverter.fromGrapeVarietiesDtoToGrapeVarieties(existingGrape));

        // When trying to create the same grape variety again
        String jsonRequest = objectMapper.writeValueAsString(existingGrape);
        mockMvc.perform(post("/api/v1/grapeVarieties/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isConflict())
                .andExpect(content().string(Messages.GRAPE_VARIETY_ALREADY_EXISTS.getMessage()));
    }
    @Test
    @DisplayName("Test create multiple grape varieties")
    void testCreateMultipleGrapeVarieties() throws Exception {
        // Given multiple grape varieties in the request
        List<GrapeVarietiesDto> grapeVarietiesList = List.of(
                new GrapeVarietiesDto("Grape3"),
                new GrapeVarietiesDto("Grape4")
        );
        String jsonRequest = objectMapper.writeValueAsString(grapeVarietiesList);

        // When creating multiple grape varieties
        mockMvc.perform(post("/api/v1/grapeVarieties/addGrapeVarieties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].name").value("Grape3"))
                .andExpect(jsonPath("$.[1].name").value("Grape4"));
    }

}