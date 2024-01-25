package backend.Wine_Project.service.wineService;

import backend.Wine_Project.dto.wineDto.WineCreateDto;
import backend.Wine_Project.model.wine.Wine;
import backend.Wine_Project.service.CrudService;


public interface WineService extends CrudService<WineCreateDto, WineCreateDto, Long> {
    Wine getById(Long id);
}
