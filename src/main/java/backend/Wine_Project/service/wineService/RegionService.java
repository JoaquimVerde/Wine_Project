package backend.Wine_Project.service.wineService;

import backend.Wine_Project.dto.regionDto.RegionCreateDto;
import backend.Wine_Project.dto.wineDto.WineReadDto;
import backend.Wine_Project.model.wine.Region;

import java.util.List;
import java.util.Set;

public interface RegionService {

    List<RegionCreateDto> getAll();

    Long create(RegionCreateDto modelCreateDto);

    List<RegionCreateDto> createRegions(List<RegionCreateDto> regions);

    Region getById(Long id);

    Set<WineReadDto> getWinesByRegion(Long regionId);

    void updateRegion(Long id, RegionCreateDto region);
}
