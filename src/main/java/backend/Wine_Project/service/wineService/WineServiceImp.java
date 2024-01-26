package backend.Wine_Project.service.wineService;

import backend.Wine_Project.converter.wineConverters.WineConverter;
import backend.Wine_Project.dto.wineDto.WineCreateDto;
import backend.Wine_Project.dto.wineDto.WineReadDto;
import backend.Wine_Project.dto.wineDto.WineUpdateDto;
import backend.Wine_Project.exceptions.WineAlreadyExistsException;
import backend.Wine_Project.exceptions.WineIdNotFoundException;
import backend.Wine_Project.model.wine.GrapeVarieties;
import backend.Wine_Project.model.wine.Region;
import backend.Wine_Project.model.wine.Wine;
import backend.Wine_Project.model.wine.WineType;
import backend.Wine_Project.repository.WineRepository;
import backend.Wine_Project.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WineServiceImp implements WineService{

private final WineRepository wineRepository;

private final GrapeVarietiesService grapeVarietiesService;
private final RegionService regionService;
private final WineTypeService wineTypeService;

@Autowired
public WineServiceImp(WineRepository wineRepository, GrapeVarietiesService grapeVarietiesService, RegionService regionService, WineTypeService wineTypeService){
    this.wineRepository = wineRepository;
    this.grapeVarietiesService = grapeVarietiesService;
    this.regionService = regionService;
    this.wineTypeService = wineTypeService;
}
    @Override
    public List<WineReadDto> getAll() {
        return WineConverter.fromListOfWinesToListOfWinesReadDto(wineRepository.findAll());
    }

    @Override
    public Long create(WineCreateDto wine) {

        Region region = regionService.getById(wine.regionId());
        WineType wineType = wineTypeService.getById(wine.wineTypeId());

        Optional<Wine> optionalWine = wineRepository.findByNameAndWineTypeAndYear(wine.name(), wineType, wine.year());
        if(optionalWine.isPresent())
            throw new WineAlreadyExistsException(Messages.WINE_ALREADY_EXISTS.getMessage());


        Wine newWine = new Wine(wine.name(), wineType, region, wine.price(), wine.alcohol(), wine.year());

        Set<GrapeVarieties> grapeVarietiesSet = new HashSet<>();

        for (Long id : wine.grapeVarietiesId()) {
            grapeVarietiesSet.add(grapeVarietiesService.getById(id));
        }

        newWine.setGrapeVarietiesList(grapeVarietiesSet);

        wineRepository.save(newWine);
        return newWine.getId();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(Long id, WineReadDto modelUpdateDto) {

    }

    @Override
    public void update(Long id, WineCreateDto wine) {

    }


    @Override
    public WineReadDto get(Long id) {
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

    public Long deleteWine(Long wineId) {

        if (!wineRepository.existsById(wineId)) {
            throw new WineIdNotFoundException(Messages.WINE_ID_NOT_FOUND.getMessage() + wineId);
        }
        wineRepository.deleteById(wineId);
        return wineId;
    }
    @Override
    public void updateWine(Long id, WineUpdateDto wine) {
        Optional<Wine> wineOptional = wineRepository.findById(id);
        if (wineOptional.isEmpty()) {
            throw new WineIdNotFoundException(Messages.WINE_ID_NOT_FOUND.getMessage() + id);
        }

        Wine wineToUpdate = wineOptional.get();

        if (wine.name() != null && wine.name().length() > 0 && !wine.name().equals(wineToUpdate.getName())) {
            wineToUpdate.setName(wine.name());
        }

        wineToUpdate.setWineType(wineTypeService.getById(wine.wineTypeId()));
        Set<GrapeVarieties> grapeVarietiesSet = new HashSet<>();
        for (Long grapeId : wine.grapeVarietiesId()) {
            grapeVarietiesSet.add(grapeVarietiesService.getById(grapeId));
        }
        wineToUpdate.setGrapeVarietiesList(grapeVarietiesSet);
        wineToUpdate.setRegion(regionService.getById(wine.regionId()));
        wineToUpdate.setPrice(wine.price());
        wineToUpdate.setAlcohol(wine.alcohol());
        wineToUpdate.setYear(wine.year());

        wineRepository.save(wineToUpdate);
    }





}
