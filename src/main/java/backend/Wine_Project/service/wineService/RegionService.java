package backend.Wine_Project.service.wineService;

import backend.Wine_Project.dto.regionDto.RegionCreateDto;
import backend.Wine_Project.model.wine.Region;
import backend.Wine_Project.service.CrudService;

public interface RegionService extends CrudService<RegionCreateDto, RegionCreateDto, Long> {

    Region getById(Long id);
}
