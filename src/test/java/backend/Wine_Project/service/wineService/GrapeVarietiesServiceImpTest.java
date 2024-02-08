package backend.Wine_Project.service.wineService;

import backend.Wine_Project.dto.grapeVarietiesDto.GrapeVarietiesDto;
import backend.Wine_Project.model.wine.GrapeVarieties;
import backend.Wine_Project.repository.GrapeVarietiesRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class GrapeVarietiesServiceImpTest {
    @Mock
    private GrapeVarietiesRepository grapeVarietiesRepository;
    @InjectMocks
    private GrapeVarietiesServiceImp grapeVarietiesServiceImp;

    @Test
    @DisplayName("Test get all")
    void testGetAll() {
        //given
        GrapeVarieties grapeVarieties = new GrapeVarieties("Touriga Nacional");
        GrapeVarieties grapeVarieties1 = new GrapeVarieties("Alvarinho");
        when(this.grapeVarietiesRepository.findAll()).thenReturn(List.of(grapeVarieties, grapeVarieties1));
        //when
        List<GrapeVarieties> grapeVarietiesList = grapeVarietiesRepository.findAll();
        //then
        assertThat(grapeVarietiesList).hasSize(2);
    }

    @Test
    @DisplayName("Test create")
    void testCreate() {

        GrapeVarieties grapeVarieties = new GrapeVarieties("Touriga Nacional");
        GrapeVarietiesDto grapeVarietiesDto = new GrapeVarietiesDto("Touriga Nacional");

        when(this.grapeVarietiesRepository.findGrapeVarietiesByName(grapeVarieties.getName())).thenReturn(Optional.empty());
        when(this.grapeVarietiesRepository.save(grapeVarieties)).thenReturn(grapeVarieties);

        Long id = grapeVarietiesServiceImp.create(grapeVarietiesDto);

        verify(grapeVarietiesRepository, times(1)).findGrapeVarietiesByName(grapeVarietiesDto.name());
        verify(grapeVarietiesRepository, times(1)).save(ArgumentMatchers.any(GrapeVarieties.class));

        assertThat(id).isEqualTo(grapeVarieties.getId());
    }

}