package backend.Wine_Project.repository;

import backend.Wine_Project.dtoWine.WineCreateDto;
import backend.Wine_Project.model.Wine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WineRepository extends JpaRepository<Wine, Long> {
    Optional<Wine> findByNameAndYearAndWineType(WineCreateDto wine);
}
