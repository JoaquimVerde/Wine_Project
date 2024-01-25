package backend.Wine_Project.service.wineService;

import backend.Wine_Project.converter.wineConverters.GrapeVarietiesConverter;
import backend.Wine_Project.exceptions.ClientIdNotFoundException;
import backend.Wine_Project.exceptions.GrapeVarietyAlreadyExistsException;
import backend.Wine_Project.dto.grapeVarietiesDto.GrapeVarietiesDto;
import backend.Wine_Project.model.wine.GrapeVarieties;
import backend.Wine_Project.repository.GrapeVarietiesRepository;
import backend.Wine_Project.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        Optional<GrapeVarieties> grapeVarietiesOptional = grapeVarietiesRepository.findGrapeVarietiesByName(modelCreateDto.name());
        if(grapeVarietiesOptional.isPresent())
            throw new GrapeVarietyAlreadyExistsException("Grape variety already exists, please use one in the database");
        GrapeVarieties grapeVarieties = new GrapeVarieties(modelCreateDto.name());
        grapeVarietiesRepository.save(grapeVarieties);
        return grapeVarieties.getId();
    }




    @Override
    public GrapeVarieties getById(Long id) {
        Optional<GrapeVarieties> optionalGrapeVarieties = grapeVarietiesRepository.findById(id);
        if (optionalGrapeVarieties.isEmpty()) {
            throw new ClientIdNotFoundException(Messages.CLIENT_ID_NOT_FOUND.getMessage() + id);
        }

        return optionalGrapeVarieties.get();
    }




}
