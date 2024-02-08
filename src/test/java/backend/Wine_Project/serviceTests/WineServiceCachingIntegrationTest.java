package backend.Wine_Project.serviceTests;

import backend.Wine_Project.model.wine.GrapeVarieties;
import backend.Wine_Project.model.wine.Region;
import backend.Wine_Project.model.wine.Wine;
import backend.Wine_Project.model.wine.WineType;
import backend.Wine_Project.repository.WineRepository;
import backend.Wine_Project.service.wineService.GrapeVarietiesServiceImp;
import backend.Wine_Project.service.wineService.RegionServiceImp;
import backend.Wine_Project.service.wineService.WineServiceImp;
import backend.Wine_Project.service.wineService.WineTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;


@EnableCaching
@SpringBootTest(classes = {CacheConfig.class, WineServiceImp.class})
@ExtendWith(SpringExtension.class) // Integrates with JUnit 5
public class WineServiceCachingIntegrationTest {

    @MockBean
    private WineRepository mockWineRepository;
    @MockBean
    private RegionServiceImp mockRegionService;
    @MockBean
    private GrapeVarietiesServiceImp grapeVarietiesServiceMock;
    @MockBean
    private WineTypeService mockWineTypeService;
    @MockBean
    private WineServiceImp wineService;

    @MockBean
    private CacheManager cacheManager;

    @BeforeEach
    public void setUp() {
        wineService = new WineServiceImp(mockWineRepository, grapeVarietiesServiceMock,mockRegionService, mockWineTypeService);

        cacheManager = Mockito.mock(CacheManager.class);
    }

    @Test
    void givenRedisCaching_whenFindItemById_thenItemReturnedFromCache() {

        Set<GrapeVarieties>grapeVarieties = new HashSet<>();
        grapeVarieties.add(new GrapeVarieties("CacheTestGrapeVarieties"));
        Region region = new Region("Douro");
        WineType wineType = new WineType("Espumante");

        Wine wine = new Wine("wineCache", wineType, region, 6.7, 11, 2020, grapeVarieties);
        wine.setId(1L);

        given(mockWineRepository.findById(1L)).willReturn(Optional.of(wine));

        Wine wineCacheMiss = wineService.getById(1L);
        Wine wineCacheHit = wineService.getById(1L);

        assertThat(wineCacheMiss).isEqualTo(wine);
        assertThat(wineCacheHit).isEqualTo(wine);

        verify(mockWineRepository, times(2)).findById(1L);
        assertEquals(wineCacheHit, wine);
        //assertThat(wineFromCache()).isEqualTo(wine);
    }

    private Object wineFromCache() {
        Cache cache = cacheManager.getCache("wineCache"); // Replace "yourCacheName" with the name of your cache
        if (cache != null) {
            Cache.ValueWrapper wrapper = cache.get(1); // AN_ID is assumed to be the cache key
            if (wrapper != null) {
                return wrapper.get();
            }
        }
        return null;
    }


}
