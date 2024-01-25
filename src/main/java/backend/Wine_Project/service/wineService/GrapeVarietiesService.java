package backend.Wine_Project.service.wineService;

import backend.Wine_Project.dto.grapeVarietiesDto.GrapeVarietiesDto;
import backend.Wine_Project.model.wine.GrapeVarieties;
import backend.Wine_Project.service.CrudService;

import java.util.List;

public interface GrapeVarietiesService {

    List<GrapeVarietiesDto> getAll();

    Long create(GrapeVarietiesDto modelCreateDto);

    GrapeVarieties getById(Long id);


}
