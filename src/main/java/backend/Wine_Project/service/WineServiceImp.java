package backend.Wine_Project.service;

import backend.Wine_Project.converter.WineConverter;
import backend.Wine_Project.dtoWine.WineCreateDto;
import backend.Wine_Project.model.Wine;
import backend.Wine_Project.repository.WineRepository;
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

}
