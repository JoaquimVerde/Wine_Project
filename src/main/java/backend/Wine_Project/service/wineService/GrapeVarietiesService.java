package backend.Wine_Project.service.wineService;

import backend.Wine_Project.dto.grapeVarietiesDto.GrapeVarietiesDto;
import backend.Wine_Project.model.wine.GrapeVarieties;
import backend.Wine_Project.service.CrudService;

import java.util.List;
import java.util.Set;

public interface GrapeVarietiesService {

    Set<GrapeVarietiesDto> getAll();

    Long create(GrapeVarietiesDto modelCreateDto);

    List<GrapeVarietiesDto> createGrapeVarieties(List<GrapeVarietiesDto> grapeVarieties);

    GrapeVarieties getById(Long id);


}
