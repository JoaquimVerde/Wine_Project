package backend.Wine_Project.service.wineService;

import backend.Wine_Project.converter.wineConverters.WineConverter;
import backend.Wine_Project.dto.wineDto.WineCreateDto;
import backend.Wine_Project.exceptions.WineIdNotFoundException;
import backend.Wine_Project.model.wine.Wine;
import backend.Wine_Project.repository.WineRepository;
import backend.Wine_Project.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WineServiceImp implements WineService{

private final WineRepository wineRepository;

@Autowired
public WineServiceImp(WineRepository wineRepository){
    this.wineRepository = wineRepository;
}
    @Override
    public List<WineCreateDto> getAll() {
        return WineConverter.fromListOfWinesToListOfWinesCreateDto(wineRepository.findAll());
    }

    @Override
    public Long create(WineCreateDto wine) {
        Wine newWine = WineConverter.fromWineCreateDtoToWine(wine);
        wineRepository.save(newWine);
        return newWine.getId();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(Long id, WineCreateDto wine) {

    }


    @Override
    public WineCreateDto get(Long id) {
        return null;
    }



    @Override
    public Wine getById(Long id) {
        Optional<Wine> optionalWine = wineRepository.findById(id);
        if (optionalWine.isEmpty()) {
            throw new WineIdNotFoundException(Messages.WINE_ID_NOT_FOUND.getMessage() + id);
        }
        return optionalWine.get();
    }

}
