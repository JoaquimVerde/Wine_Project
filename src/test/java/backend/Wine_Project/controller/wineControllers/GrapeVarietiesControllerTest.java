package backend.Wine_Project.controller.wineControllers;

import backend.Wine_Project.model.wine.GrapeVarieties;
import backend.Wine_Project.repository.GrapeVarietiesRepository;
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
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;



import static org.hamcrest.Matchers.hasSize;

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
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/grapeVarieties/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",hasSize(0)));
    }
    @Test
    @DisplayName("Test create a grape variety and return status code 201")
    void testCreateGrapeVarietyAndReturnStatus201()throws Exception{
        String grapeJason = "{\"name\": \"Toriga Nacional\"}";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/grapeVarieties/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(grapeJason))
                .andExpect(status().isCreated())
                .andReturn();
        // Then
        String responseContent = result.getResponse().getContentAsString();
        // grape asserts
        assertThat(responseContent).isEqualTo("New grape variety added successfully");

    }

}