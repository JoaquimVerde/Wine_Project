package backend.Wine_Project.service.wineService;

import backend.Wine_Project.dto.regionDto.RegionCreateDto;
import backend.Wine_Project.dto.wineDto.WineReadDto;
import backend.Wine_Project.model.wine.Region;
import backend.Wine_Project.service.CrudService;

import java.util.Set;

public interface RegionService extends CrudService<RegionCreateDto, RegionCreateDto, Long> {

    Region getById(Long id);

    Set<WineReadDto> getWinesByRegion(Long regionId);
}
