package backend.Wine_Project.serviceTests;

import backend.Wine_Project.converter.wineConverters.WineTypeConverter;
import backend.Wine_Project.dto.itemDto.ItemCreateDto;
import backend.Wine_Project.dto.itemDto.ItemGetDto;
import backend.Wine_Project.dto.wineDto.WineCreateDto;
import backend.Wine_Project.dto.wineDto.WineReadRatingDto;
import backend.Wine_Project.model.Item;
import backend.Wine_Project.model.wine.GrapeVarieties;
import backend.Wine_Project.model.wine.Region;
import backend.Wine_Project.model.wine.Wine;
import backend.Wine_Project.model.wine.WineType;
import backend.Wine_Project.repository.*;
import backend.Wine_Project.service.itemService.ItemServiceImp;
import backend.Wine_Project.service.wineService.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
class ItemServiceImpTest {
    @Mock
    private ItemRepository itemRepository;

    @Mock
    private WineService wineService;

    @InjectMocks
    private ItemServiceImp itemService;


    @Test
    @DisplayName("Test get all")
    void testGetAll() {
        Region region = new Region("Douro");
        WineType wineType = new WineType("Red");
        GrapeVarieties grapeVarieties = new GrapeVarieties("Merlot");
        Wine wine = new Wine("Wine", wineType, region,10,12, 2010, Set.of(grapeVarieties));
        Wine wine1 = new Wine("Wine1", wineType, region,10,12, 2010, Set.of(grapeVarieties));
        List<Item> mockItems = List.of(new Item(wine, 1), new Item(wine1,2));
        when(this.itemRepository.findAll()).thenReturn(mockItems);


        List<ItemGetDto> items = itemService.getAll();

        verify(itemRepository,times(1)).findAll();
        assertThat(items).hasSize(2);

    }
    @Test
    @DisplayName("Test create item with valid input")
    void testCreateWithValidInput() {
        // Mocking wine retrieval

        Region region = new Region("Douro");
        WineType wineType = new WineType("Red");
        GrapeVarieties grapeVarieties = new GrapeVarieties("Merlot");
        Wine wine = new Wine("Wine", wineType, region,10,12, 2010, Set.of(grapeVarieties));
        Long wineId = 1L;
        when(wineService.getById(wineId)).thenReturn(wine);
        when(itemRepository.findByWineAndQuantity(wine, 1)).thenReturn(Optional.empty());
        Item itemToSave = new Item(wine, 1);
        when(itemRepository.save(itemToSave)).thenReturn(itemToSave);

        // Call create method
        Long itemId = itemService.create(new ItemCreateDto(wineId, 1));

        // Verify interactions
        verify(wineService, times(1)).getById(wineId);
        verify(itemRepository, times(1)).findByWineAndQuantity(wine, 1);
        //verify(itemRepository, times(1)).save(itemToSave);

        // Assertions
       assertThat(itemId).isEqualTo(itemToSave.getId());
    }





}