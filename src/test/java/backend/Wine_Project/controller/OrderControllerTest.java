package backend.Wine_Project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }


    @Test
    void testGetAllOrders() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/wine_orders/"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateOrder() throws Exception {
        String orderJson = "{\"id\":1,\"itemsSet\":[{\"id\":1,\"quantity\":5,\"wine\":{\"id\":1,\"name\":\"Test Wine\",\"wineType\":{\"id\":1,\"type\":\"Red\"},\"region\":{\"id\":1,\"region\":\"Test Region\"},\"grapeVarietiesList\":[{\"id\":1,\"variety\":\"Test Variety\"}],\"ratingAvg\":20.0,\"price\":20.0,\"alcohol\":20.0,\"year\":2023}}],\"client\":{\"id\":1,\"name\":\"Test Client\",\"email\":\"test@client.com\",\"phoneNumber\":1234567890},\"totalAmount\":100.0}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/wine_orders/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(status().isCreated());
    }

    @Test
    void testDeleteOrder() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/wine_orders/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateOrder() throws Exception {
        String orderJson = "{\"id\":1,\"itemsSet\":[{\"id\":1,\"quantity\":5,\"wine\":{\"id\":1,\"name\":\"Test Wine\",\"wineType\":{\"id\":1,\"type\":\"Red\"},\"region\":{\"id\":1,\"region\":\"Test Region\"},\"grapeVarietiesList\":[{\"id\":1,\"variety\":\"Test Variety\"}],\"ratingAvg\":20.0,\"price\":20.0,\"alcohol\":20.0,\"year\":2023}}],\"client\":{\"id\":1,\"name\":\"Test Client\",\"email\":\"test@client.com\",\"phoneNumber\":1234567890},\"totalAmount\":100.0}";

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/wine_orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(status().isOk());
    }
}