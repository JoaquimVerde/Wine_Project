package backend.Wine_Project.serviceTests;

import backend.Wine_Project.dto.clientDto.ClientCreateDto;
import backend.Wine_Project.exceptions.notFound.ClientIdNotFoundException;
import backend.Wine_Project.exceptions.alreadyExists.EmailAlreadyExistsException;
import backend.Wine_Project.model.Client;
import backend.Wine_Project.repository.ClientRepository;
import backend.Wine_Project.service.clientService.ClientServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClientServiceImpTest {

    @InjectMocks
    private ClientServiceImp clientService;

    @Mock
    private ClientRepository clientRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllClientsReturnsExpectedClients() {
        Client client1 = new Client();
        Client client2 = new Client();
        when(clientRepository.findAll()).thenReturn(Arrays.asList(client1, client2));

    }

    @Test
    public void createClientSuccessfullyWhenEmailNotExists() {
        ClientCreateDto clientCreateDto = new ClientCreateDto("test@test.com", "John Doe", 30);
        when(clientRepository.findClientByEmail(clientCreateDto.email())).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> clientService.create(clientCreateDto));
    }

    @Test
    public void createClientThrowsExceptionWhenEmailExists() {
        ClientCreateDto clientCreateDto = new ClientCreateDto("test@test.com", "John Doe", 30);
        when(clientRepository.findClientByEmail(clientCreateDto.email())).thenReturn(Optional.of(new Client()));

        assertThrows(EmailAlreadyExistsException.class, () -> clientService.create(clientCreateDto));
    }

    @Test
    public void getClientByIdSuccessfullyWhenIdExists() {
        Long id = 1L;
        when(clientRepository.findById(id)).thenReturn(Optional.of(new Client()));

        assertDoesNotThrow(() -> clientService.getById(id));
    }

    @Test
    public void getClientByIdThrowsExceptionWhenIdNotExists() {
        Long id = 1L;
        when(clientRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ClientIdNotFoundException.class, () -> clientService.getById(id));
    }
}