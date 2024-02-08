package backend.Wine_Project.serviceTests;

import backend.Wine_Project.dto.ratingDto.RatingCreateDto;
import backend.Wine_Project.exceptions.alreadyExists.RatingAlreadyExistsException;
import backend.Wine_Project.model.Client;
import backend.Wine_Project.model.Rating;
import backend.Wine_Project.model.wine.Wine;
import backend.Wine_Project.model.wine.WineType;
import backend.Wine_Project.repository.RatingRepository;
import backend.Wine_Project.service.LMStudioService;
import backend.Wine_Project.service.clientService.ClientService;
import backend.Wine_Project.service.ratingService.RatingServiceImp;
import backend.Wine_Project.service.wineService.WineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RatingServiceImpTest {

    @InjectMocks
    private RatingServiceImp ratingService;

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private ClientService clientService;

    @Mock
    private WineService wineService;
    @Mock
    private LMStudioService lmStudioService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createRatingSuccessfullyWhenRatingNotExists() {
        RatingCreateDto ratingCreateDto = new RatingCreateDto(1L, 1L, 5.0);
        Client client = new Client();
        client.setRatedWines(new HashSet<>());
        when(clientService.getById(anyLong())).thenReturn(client);

        Wine wine = new Wine();
        WineType wineType = new WineType();
        wineType.setName("Red");
        wine.setWineType(wineType);
        when(wineService.getById(anyLong())).thenReturn(wine);

        when(ratingRepository.findByClientAndWine(any(Client.class), any(Wine.class))).thenReturn(Optional.empty());

        when(lmStudioService.callLocalLMStudio(anyString())).thenReturn("{\"choices\": [{\"text\": \"This is a test review.\"}]}");
        assertDoesNotThrow(() -> ratingService.create(ratingCreateDto));
    }

    @Test
    public void createRatingThrowsExceptionWhenRatingExists() {
        RatingCreateDto ratingCreateDto = new RatingCreateDto(1L, 1L, 5.0);
        when(clientService.getById(anyLong())).thenReturn(new Client());
        when(wineService.getById(anyLong())).thenReturn(new Wine());
        when(ratingRepository.findByClientAndWine(any(Client.class), any(Wine.class))).thenReturn(Optional.of(new Rating()));

        assertThrows(RatingAlreadyExistsException.class, () -> ratingService.create(ratingCreateDto));
    }


    @Test
    public void createRatingThrowsExceptionWhenRatingCreateDtoIsNull() {
        assertThrows(NullPointerException.class, () -> ratingService.create(null));
    }

    @Test
    public void createRatingThrowsExceptionWhenClientIdIsNull() {
        RatingCreateDto ratingCreateDto = new RatingCreateDto(null, 1L, 5.0);
        assertThrows(NullPointerException.class, () -> ratingService.create(ratingCreateDto));
    }

    @Test
    public void createRatingThrowsExceptionWhenWineIdIsNull() {
        RatingCreateDto ratingCreateDto = new RatingCreateDto(1L, null, 5.0);
        assertThrows(NullPointerException.class, () -> ratingService.create(ratingCreateDto));
    }

    @Test
    public void createRatingThrowsExceptionWhenClientServiceGetByIdReturnsNull() {
        RatingCreateDto ratingCreateDto = new RatingCreateDto(1L, 1L, 5.0);
        when(clientService.getById(anyLong())).thenReturn(null);
        assertThrows(NullPointerException.class, () -> ratingService.create(ratingCreateDto));
    }

    @Test
    public void createRatingThrowsExceptionWhenWineServiceGetByIdReturnsNull() {
        RatingCreateDto ratingCreateDto = new RatingCreateDto(1L, 1L, 5.0);
        when(wineService.getById(anyLong())).thenReturn(null);
        assertThrows(NullPointerException.class, () -> ratingService.create(ratingCreateDto));
    }

    @Test
    public void createRatingThrowsExceptionWhenLmStudioServiceCallLocalLMStudioReturnsInvalidJson() {
        RatingCreateDto ratingCreateDto = new RatingCreateDto(1L, 1L, 5.0);
        Client client = new Client();
        client.setRatedWines(new HashSet<>());
        when(clientService.getById(anyLong())).thenReturn(client);

        Wine wine = new Wine();
        WineType wineType = new WineType();
        wineType.setName("Red");
        wine.setWineType(wineType);
        when(wineService.getById(anyLong())).thenReturn(wine);

        when(ratingRepository.findByClientAndWine(any(Client.class), any(Wine.class))).thenReturn(Optional.empty());

        when(lmStudioService.callLocalLMStudio(anyString())).thenReturn("invalid json string");
        assertThrows(RuntimeException.class, () -> ratingService.create(ratingCreateDto));
    }

}