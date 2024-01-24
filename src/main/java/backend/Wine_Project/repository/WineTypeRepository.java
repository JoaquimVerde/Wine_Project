package backend.Wine_Project.repository;

import backend.Wine_Project.model.WineType;
import backend.Wine_Project.wineTypeDto.WineTypeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WineTypeRepository extends JpaRepository<WineType, Long> {
    Optional<WineType> findByName(WineTypeDto modelCreateDto);
}
