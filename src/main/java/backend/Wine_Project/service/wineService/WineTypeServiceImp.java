package backend.Wine_Project.service.wineService;

import backend.Wine_Project.converter.wineConverters.WineConverter;
import backend.Wine_Project.converter.wineConverters.WineTypeConverter;
import backend.Wine_Project.dto.wineDto.WineReadDto;
import backend.Wine_Project.exceptions.WineTypeAlreadyExistsException;
import backend.Wine_Project.exceptions.WineTypeIdNotFoundException;
import backend.Wine_Project.model.wine.WineType;
import backend.Wine_Project.repository.WineTypeRepository;
import backend.Wine_Project.dto.wineTypeDto.WineTypeCreateDto;
import backend.Wine_Project.util.Messages;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class WineTypeServiceImp implements WineTypeService{
    private final WineTypeRepository wineTypeRepository;

    public WineTypeServiceImp(WineTypeRepository wineTypeRepository) {
        this.wineTypeRepository = wineTypeRepository;
    }

    @Override
    public List<WineTypeCreateDto> getAll() {
        List<WineType> wineTypes = wineTypeRepository.findAll();
        return WineTypeConverter.fromWineTypeListToWineTypeDtoList(wineTypes);

    }

    @Override
    public Long create(WineTypeCreateDto wineTypeCreateDto) {
        Optional<WineType> wineTypeOptional = wineTypeRepository.findByName(wineTypeCreateDto.name());
        if(wineTypeOptional.isPresent())
            throw new WineTypeAlreadyExistsException("Wine type already exists!");
        WineType wineType = new WineType(wineTypeCreateDto.name());
        wineTypeRepository.save(wineType);
        return wineType.getId();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(Long id, WineTypeCreateDto modelUpdateDto) {

    }

    @Override
    public WineTypeCreateDto get(Long id) {
        return null;
    }

    @Override
    public WineType getById(Long id) {
        Optional<WineType> optionalWineType = wineTypeRepository.findById(id);
        if (optionalWineType.isEmpty()) {
            throw new WineTypeIdNotFoundException(Messages.WINE_TYPE_ID_NOT_FOUND.getMessage() + id);
        }
        return optionalWineType.get();
    }

    @Override
    public Set<WineReadDto> getWinesByType(Long wineTypeId) {
        Optional<WineType> optionalWineType = wineTypeRepository.findById(wineTypeId);
        if (optionalWineType.isEmpty()) {
            throw new WineTypeIdNotFoundException(Messages.WINE_TYPE_ID_NOT_FOUND.getMessage() + wineTypeId);
        }
        return WineConverter.fromSetOfWinesToSetOfWinesReadDto(wineTypeRepository.findWinesByType(wineTypeId));
    }
}
