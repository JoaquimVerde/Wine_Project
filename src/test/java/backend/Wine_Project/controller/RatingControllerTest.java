package backend.Wine_Project.controller;

import backend.Wine_Project.repository.RatingRepository;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RatingControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RatingRepository ratingRepository;

    private static ObjectMapper objectMapper;

    @BeforeAll
    public static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @BeforeEach
    void ini() {
        ratingRepository.deleteAll();
        ratingRepository.resetAutoIncrement();
    }

    @Test
    @DisplayName("Test get all ratings when no ratings on database returns a empty list")
    void testGetAllRatingsWhenNoRatingsOnDatabase() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/ratings/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("Test create a rating and returns a status code 201")
    void testCreateRatingAndReturnsStatus201() throws Exception {
        //Given
        String ratingJason = "{\"clientId\": 1, \"wineId\": 1, \"rate\": 5}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/ratings/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ratingJason))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Test create a rating with invalid client id and returns a status code 404")
    void testCreateRatingWithInvalidClientIdAndReturnsStatus404() throws Exception {
        //Given
        String ratingJason = "{\"clientId\": 1, \"wineId\": 1, \"rate\": 5}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/ratings/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ratingJason))
                .andExpect(status().isNotFound())
                .andExpect(content().string(Messages.CLIENT_ID_NOT_FOUND.getMessage() + 1));
    }

    @Test
    @DisplayName("Test create a rating with invalid wine id and returns a status code 404")
    void testCreateRatingWithInvalidWineIdAndReturnsStatus404() throws Exception {
        //Given
        String ratingJason = "{\"clientId\": 1, \"wineId\": 1, \"rate\": 5}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/ratings/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ratingJason))
                .andExpect(status().isNotFound())
                .andExpect(content().string(Messages.CLIENT_ID_NOT_FOUND.getMessage() + 1));
    }

    @Test
    @DisplayName("Test create a rating with invalid rating and returns a status code 400")
    void testCreateRatingWithInvalidRatingAndReturnsStatus400() throws Exception {
        //Given
        String ratingJason = "{\"clientId\": 1, \"wineId\": 1, \"rate\": 6}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/ratings/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ratingJason))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test create a rating with invalid rating and returns a status code 400")
    void testCreateRatingWithInvalidRatingAndReturnsStatus400_2() throws Exception {
        //Given
        String ratingJason = "{\"clientId\": 1, \"wineId\": 1, \"rate\": -1}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/ratings/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ratingJason))
                .andExpect(status().isBadRequest());
    }


}