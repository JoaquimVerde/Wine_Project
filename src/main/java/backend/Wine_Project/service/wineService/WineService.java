package backend.Wine_Project.service.wineService;

import backend.Wine_Project.dto.wineDto.WineCreateDto;
import backend.Wine_Project.dto.wineDto.WineReadDto;
import backend.Wine_Project.dto.wineDto.WineUpdateDto;
import backend.Wine_Project.model.wine.Wine;
import backend.Wine_Project.service.CrudService;

import java.util.List;
import java.util.Set;


public interface WineService extends CrudService<WineReadDto, WineCreateDto, Long> {
    List<WineCreateDto> createWines(List<WineCreateDto> wines);

    void update(Long id, WineCreateDto wine);

    Wine getById(Long id);

    void updateWine(Long id, WineUpdateDto wine);

}
