package backend.Wine_Project.service.wineService;

import backend.Wine_Project.dto.wineDto.WineCreateDto;
import backend.Wine_Project.dto.wineDto.WineReadDto;
import backend.Wine_Project.dto.wineDto.WineUpdateDto;
import backend.Wine_Project.model.wine.Wine;

import java.util.List;


public interface WineService {

    List<WineCreateDto> createWines(List<WineCreateDto> wines);


    List<WineReadDto> getAll(int pageNumber, int pageSize);

    WineCreateDto create(WineCreateDto wine);

    Wine getById(Long id);

    void updateWine(Long id, WineUpdateDto wine);

    void saveWine(Wine wine);
}
