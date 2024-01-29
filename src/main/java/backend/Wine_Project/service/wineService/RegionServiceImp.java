package backend.Wine_Project.service.wineService;

import backend.Wine_Project.converter.wineConverters.RegionConverter;
import backend.Wine_Project.converter.wineConverters.WineConverter;
import backend.Wine_Project.dto.wineDto.WineReadDto;
import backend.Wine_Project.exceptions.RegionAlreadyExistsException;
import backend.Wine_Project.exceptions.RegionIdNotFoundException;
import backend.Wine_Project.model.wine.Region;
import backend.Wine_Project.dto.regionDto.RegionCreateDto;
import backend.Wine_Project.repository.RegionRepository;
import backend.Wine_Project.util.Messages;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RegionServiceImp implements RegionService{
    public final RegionRepository regionRepository;

    public RegionServiceImp(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @Override
    public List<RegionCreateDto> getAll() {
        return RegionConverter.fromRegionListToRegionDtoList(regionRepository.findAll());
    }

    @Override
    public Long create(RegionCreateDto modelCreateDto) {
        Optional<Region> regionOptional = regionRepository.findByName(modelCreateDto.name());
        if(regionOptional.isPresent())
            throw new RegionAlreadyExistsException("Region already exist, please use the region of database");
        Region region = new Region(modelCreateDto.name());
        regionRepository.save(region);

        return region.getId();

    }
    @Override
    public List<RegionCreateDto> createRegions(List<RegionCreateDto> regions){
        for (RegionCreateDto region: regions) {
            Optional<Region> regionOptional = regionRepository.findByName(region.name());
            if(regionOptional.isPresent())
                throw new RegionAlreadyExistsException("Region already exist, please use the region of database");
            Region newRegion = new Region(region.name());
            regionRepository.save(newRegion);
        }
        return regions;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(Long id, RegionCreateDto modelUpdateDto) {

    }

    @Override
    public RegionCreateDto get(Long id) {
        return null;
    }

    @Override
    public Region getById(Long id) {
        Optional<Region> optionalRegion = regionRepository.findById(id);
        if (optionalRegion.isEmpty()) {
            throw new RegionIdNotFoundException(Messages.REGION_ID_NOT_FOUND.getMessage() + id);
        }
        return optionalRegion.get();
    }

    @Override
    public Set<WineReadDto> getWinesByRegion(Long regionId) {
        Optional<Region> optionalRegion = regionRepository.findById(regionId);
        if (optionalRegion.isEmpty()) {
            throw new RegionIdNotFoundException(Messages.REGION_ID_NOT_FOUND.getMessage() + regionId);
        }
        return WineConverter.fromSetOfWinesToSetOfWinesReadDto(regionRepository.findWinesByRegion(regionId));
    }
}
