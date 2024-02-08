package backend.Wine_Project.serviceTests;

import backend.Wine_Project.dto.wineDto.WineCreateDto;
import backend.Wine_Project.exceptions.YearCannotBeFutureException;
import backend.Wine_Project.repository.WineRepository;
import backend.Wine_Project.service.ratingService.RatingServiceImp;
import backend.Wine_Project.service.shopppingCartService.ShoppingCartService;
import backend.Wine_Project.service.shopppingCartService.ShoppingCartServiceImp;
import backend.Wine_Project.service.wineService.GrapeVarietiesService;
import backend.Wine_Project.service.wineService.RegionService;
import backend.Wine_Project.service.wineService.WineServiceImp;
import backend.Wine_Project.service.wineService.WineTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class WineServiceTest {

    private WineServiceImp wineServiceImp;
    @MockBean
    private WineRepository wineRepositoryMock;
    @MockBean
    private GrapeVarietiesService grapeVarietiesServiceMock;
    @MockBean
    private RegionService regionServiceMock;
    @MockBean
    private WineTypeService wineTypeServiceMock;


    @BeforeEach
    public void setUp() {
        wineServiceImp = new WineServiceImp(wineRepositoryMock, grapeVarietiesServiceMock, regionServiceMock, wineTypeServiceMock);
    }

    @Test
    void createWineWithFutureYearThrowsException() {

        //given
        Set<Long> grapeVarietiesId = new HashSet<>();
        grapeVarietiesId.add(1L);
        WineCreateDto wineCreateDto = new WineCreateDto("Papa Figos", 1L, grapeVarietiesId, 1L, 7.99, 11, 2028);

        //then
        assertThrows(YearCannotBeFutureException.class, () -> wineServiceImp.create(wineCreateDto));
        assertEquals("Year cannot be future.", assertThrows(YearCannotBeFutureException.class, () -> wineServiceImp.create(wineCreateDto)).getMessage());
    }


}
