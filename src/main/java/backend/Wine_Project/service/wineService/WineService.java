package backend.Wine_Project.service.wineService;

import backend.Wine_Project.dto.wineDto.WineCreateDto;
import backend.Wine_Project.dto.wineDto.WineReadDto;
import backend.Wine_Project.model.wine.Wine;
import backend.Wine_Project.service.CrudService;

import java.util.Set;


public interface WineService extends CrudService<WineReadDto, WineCreateDto, Long> {
    void update(Long id, WineCreateDto wine);

    Wine getById(Long id);

}
