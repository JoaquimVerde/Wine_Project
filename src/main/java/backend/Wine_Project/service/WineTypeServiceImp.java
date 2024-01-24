package backend.Wine_Project.service;

import backend.Wine_Project.converter.WineTypeConverter;
import backend.Wine_Project.model.Wine;
import backend.Wine_Project.model.WineType;
import backend.Wine_Project.repository.WineTypeRepository;
import backend.Wine_Project.wineTypeDto.WineTypeDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WineTypeServiceImp implements WineTypeService{
    private final WineTypeRepository wineTypeRepository;

    public WineTypeServiceImp(WineTypeRepository wineTypeRepository) {
        this.wineTypeRepository = wineTypeRepository;
    }

    @Override
    public List<WineTypeDto> getAll() {
        List<WineType> wineTypes = wineTypeRepository.findAll();
        return WineTypeConverter.fromWineTypeListToWineTypeDtoList(wineTypes);

    }

    @Override
    public Long create(WineTypeDto modelCreateDto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(Long id, WineTypeDto modelUpdateDto) {

    }

    @Override
    public WineTypeDto get(Long id) {
        return null;
    }
}
