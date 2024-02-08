package backend.Wine_Project.controller;


import backend.Wine_Project.model.Client;
import backend.Wine_Project.repository.ClientRepository;
import backend.Wine_Project.util.Messages;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ClientRepository clientRepository;

    private static ObjectMapper objectMapper;


    @BeforeAll
    public static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @BeforeEach
    void ini() {
        clientRepository.deleteAll();
        clientRepository.resetAutoIncrement();
    }

    @Test
    @DisplayName("Test get all clients when no clients on database returns a empty list")
    void testGetAllClientsWhenNoClientsOnDatabase() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/clients/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("Test create a client and returns a status code 201")
    void testCreateClientAndReturnsStatus201() throws Exception {
        //Given
        String clientJason = "{\"name\": \"Joao\", \"email\": \"j@eee.com\", \"nif\": 123456789}";
        //When
        MvcResult result = mockMvc.perform(post("/api/v1/clients/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJason))
                .andExpect(status().isCreated())
                .andReturn();

        // Then
        String responseContent = result.getResponse().getContentAsString();
        Long clientId = objectMapper.readValue(responseContent, Long.class);

        // Client asserts
        assertThat(clientId).isEqualTo(1);

    }

    @Test
    @DisplayName("test get all clients when 2 clients in database")
    void testGetAllClientsWhen2ClientsInDatabase() throws Exception {
        //Create 2 Clients
        mockMvc.perform(post("/api/v1/clients/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Joao\", \"email\": \"j@eee.com\", \"nif\": 123456789}"));
        mockMvc.perform(post("/api/v1/clients/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Joao\", \"email\": \"j2@eee.com\", \"nif\": 124456789}"));

        // get all clients
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/clients/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));

    }

    @Test
    @DisplayName("test create a list of clients and returns a list of clients dtos")
    void testCreateListOfClientsAndReturnsListOfClientsDTOS() throws Exception {
        mockMvc.perform(post("/api/v1/clients/addClients")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[\n" +
                        "    {\n" +
                        "    \"name\": \"Joaquim Verde\",\n" +
                        "    \"email\": \"jverde@email.com\",\n" +
                        "    \"nif\": 111727339\n" +
                        "    },\n" +
                        "\n" +
                        "    {\n" +
                        "    \"name\": \"André Nunes\",\n" +
                        "    \"email\": \"anunes@email.com\",\n" +
                        "    \"nif\": 112388511\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"name\": \"Cláudia Lamas\",\n" +
                        "    \"email\": \"clamas@email.com\",\n" +
                        "    \"nif\": 119478511\n" +
                        "    },\n" +
                        "    {\n" +
                        "    \"name\": \"João Moutinho\",\n" +
                        "    \"email\": \"jmoutinho@email.com\",\n" +
                        "    \"nif\": 116388274\n" +
                        "    }\n" +
                        "]"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/clients/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)));

    }


    @ParameterizedTest
    @DisplayName("Test create a client with invalid request content")
    @ValueSource(strings = {
            "{\"invalid_field\": \"Invalid Client\"}",
            "{\"name\": \"John\"}",
            "{\"name\": \"John\", \"email\": \"invalid_email\"}",
            "{\"name\": \"John\",\"email\":\"john@example.com\" \"nif\": \"invalid_nif\"}"
    })
    void testCreateClientWithInvalidRequestContent(String invalidJsonRequest) throws Exception {
        // When trying to create a client with invalid content
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/clients/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test create a client with an email that already exists and returns status code 409")
    void testCreateClientWithEmailConflict() throws Exception {
        // Given an existing client with the same email
        Client client = new Client("John Doe", "john@example.com", 123456789);
        clientRepository.save(client);


        // When trying to create a client with the same email
        MvcResult result = mockMvc.perform(post("/api/v1/clients/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Jane Doe\", \"email\": \"john@example.com\", \"nif\": 987654321}"))
                .andExpect(status().isConflict())
                .andReturn();

        // Then
        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEqualTo(Messages.CLIENT_EMAIL_ALREADY_EXISTS.getMessage());
    }

    @Test
    @DisplayName("Test create a client with a NIF that already exists and returns status code 409")
    void testCreateClientWithNifConflict() throws Exception {
        // Given an existing client with the same NIF
        Client client = new Client("John Doe", "john@example.com", 123456789);
        clientRepository.save(client);


        // When trying to create a client with the same NIF
        MvcResult result = mockMvc.perform(post("/api/v1/clients/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Jane Doe\", \"email\": \"jane@example.com\", \"nif\": 123456789}"))
                .andExpect(status().isConflict())
                .andReturn();

        // Then
        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEqualTo(Messages.CLIENT_NIF_ALREADY_EXISTS.getMessage());
    }


}