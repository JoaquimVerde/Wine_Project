package backend.Wine_Project.service.wineService;

import backend.Wine_Project.converter.wineConverters.RegionConverter;
import backend.Wine_Project.exceptions.RegionAlreadyExistsException;
import backend.Wine_Project.model.wine.Region;
import backend.Wine_Project.dto.regionDto.RegionDto;
import backend.Wine_Project.repository.RegionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class RegionServiceImp implements RegionService{
    public final RegionRepository regionRepository;

    public RegionServiceImp(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @Override
    public List<RegionDto> getAll() {
        return RegionConverter.fromRegionListToRegionDtoList(regionRepository.findAll());
    }

    @Override
    public Long create(RegionDto modelCreateDto) {
        Optional<Region> regionOptional = regionRepository.findByName(modelCreateDto.name());
        if(regionOptional.isPresent())
            throw new RegionAlreadyExistsException("Region already exist, please use the region of database");
        Region region = RegionConverter.fromRegionDtoToRegion(modelCreateDto);
        regionRepository.save(region);

        return region.getId();

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(Long id, RegionDto modelUpdateDto) {

    }

    @Override
    public RegionDto get(Long id) {
        return null;
    }
}
