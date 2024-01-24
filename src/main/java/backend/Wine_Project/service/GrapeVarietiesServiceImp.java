package backend.Wine_Project.service;

import backend.Wine_Project.converter.GrapeVarietiesConverter;
import backend.Wine_Project.exceptions.GrapeVarietyAlreadyExistsException;
import backend.Wine_Project.grapeVarietiesDto.GrapeVarietiesDto;
import backend.Wine_Project.model.GrapeVarieties;
import backend.Wine_Project.repository.GrapeVarietiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GrapeVarietiesServiceImp implements GrapeVarietiesService{
    private final GrapeVarietiesRepository grapeVarietiesRepository;
    @Autowired
    public GrapeVarietiesServiceImp(GrapeVarietiesRepository grapeVarietiesRepository) {
        this.grapeVarietiesRepository = grapeVarietiesRepository;
    }

    @Override
    public List<GrapeVarietiesDto> getAll() {
        return GrapeVarietiesConverter.fromGrapeVarietiesListToGrapeVarietiesDtoList(grapeVarietiesRepository.findAll());
    }

    @Override
    public Long create(GrapeVarietiesDto modelCreateDto) {
        Optional<GrapeVarieties> grapeVarietiesOptional = grapeVarietiesRepository.findByName(modelCreateDto.name());
        if(grapeVarietiesOptional.isPresent())
            throw new GrapeVarietyAlreadyExistsException("Grape variety already exists, please use one in the database");
        GrapeVarieties grapeVarieties = GrapeVarietiesConverter.fromGrapeVarietiesDtoToGrapeVarieties(modelCreateDto);
        grapeVarietiesRepository.save(grapeVarieties);
        return grapeVarieties.getId();
    }


    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(Long id, GrapeVarietiesDto modelUpdateDto) {

    }

    @Override
    public GrapeVarietiesDto get(Long id) {
        return null;
    }
}
