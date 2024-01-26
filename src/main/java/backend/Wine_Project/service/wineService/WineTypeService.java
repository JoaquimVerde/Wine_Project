package backend.Wine_Project.service.wineService;

import backend.Wine_Project.dto.wineDto.WineReadDto;
import backend.Wine_Project.dto.wineTypeDto.WineTypeCreateDto;
import backend.Wine_Project.model.wine.WineType;
import backend.Wine_Project.service.CrudService;

import java.util.Set;

public interface WineTypeService extends CrudService<WineTypeCreateDto, WineTypeCreateDto,Long> {
    WineType getById(Long id);

    Set<WineReadDto> getWinesByType(Long wineTypeId);
}
