package backend.Wine_Project.serviceTests;

import backend.Wine_Project.dto.itemDto.ItemCreateDto;
import backend.Wine_Project.dto.itemDto.ItemGetDto;
import backend.Wine_Project.exceptions.alreadyExists.ItemAlreadyExistsException;
import backend.Wine_Project.exceptions.notFound.ItemIdNotFoundException;
import backend.Wine_Project.model.Item;
import backend.Wine_Project.model.wine.Wine;
import backend.Wine_Project.model.wine.WineType;
import backend.Wine_Project.repository.ItemRepository;
import backend.Wine_Project.service.itemService.ItemServiceImp;
import backend.Wine_Project.service.wineService.WineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ItemServiceImpTest {

    @InjectMocks
    private ItemServiceImp itemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private WineService wineService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllItemsReturnsExpectedItems() {
        WineType wineType1 = new WineType("Red");
        WineType wineType2 = new WineType("White");
        Wine wine1 = new Wine();
        wine1.setWineType(wineType1);
        Wine wine2 = new Wine();
        wine2.setWineType(wineType2);
        Item item1 = new Item(wine1, 3);
        Item item2 = new Item(wine2, 4);
        when(itemRepository.findAll()).thenReturn(Arrays.asList(item1, item2));

        List<ItemGetDto> findAllItems = itemService.getAll();

        assertEquals(2, findAllItems.size());
    }

    @Test
    public void createItemSuccessfullyWhenItemNotExists() {
        Wine wine = new Wine();
        ItemCreateDto itemCreateDto = new ItemCreateDto(1L, 10);
        when(wineService.getById(anyLong())).thenReturn(wine);
        when(itemRepository.findByWineAndQuantity(any(Wine.class), anyInt())).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> itemService.create(itemCreateDto));
    }

    @Test
    public void createItemThrowsExceptionWhenItemExists() {
        Wine wine = new Wine();
        ItemCreateDto itemCreateDto = new ItemCreateDto(1L, 10);
        when(wineService.getById(anyLong())).thenReturn(wine);
        Item existingItem = new Item(wine, 10);
        when(itemRepository.findByWineAndQuantity(any(Wine.class), anyInt())).thenReturn(Optional.of(existingItem));

        assertThrows(ItemAlreadyExistsException.class, () -> itemService.create(itemCreateDto));
    }

    @Test
    public void getItemByIdSuccessfullyWhenIdExists() {
        Long id = 1L;
        when(itemRepository.findById(id)).thenReturn(Optional.of(new Item()));

        assertDoesNotThrow(() -> itemService.getById(id));
    }

    @Test
    public void getItemByIdThrowsExceptionWhenIdNotExists() {
        Long id = 1L;
        when(itemRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ItemIdNotFoundException.class, () -> itemService.getById(id));
    }

    @Test
    public void createItemThrowsExceptionWhenItemCreateDtoIsNull() {
        assertThrows(NullPointerException.class, () -> itemService.create(null));
    }

    @Test
    public void createItemThrowsExceptionWhenWineIdIsNull() {
        ItemCreateDto itemCreateDto = new ItemCreateDto(null, 10);
        assertThrows(IllegalArgumentException.class, () -> itemService.create(itemCreateDto));
    }

    @Test
    public void createItemThrowsExceptionWhenWineIdDoesNotExist() {
        Long nonExistingWineId = 9999L;
        ItemCreateDto itemCreateDto = new ItemCreateDto(nonExistingWineId, 10);
        when(wineService.getById(nonExistingWineId)).thenReturn(null);
        assertThrows(NullPointerException.class, () -> itemService.create(itemCreateDto));
    }

    @Test
    public void getItemByIdThrowsExceptionWhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> itemService.getById(null));
    }
}