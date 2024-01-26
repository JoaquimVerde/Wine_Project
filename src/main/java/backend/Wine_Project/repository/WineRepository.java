package backend.Wine_Project.repository;

import backend.Wine_Project.model.wine.Wine;
import backend.Wine_Project.model.wine.WineType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.Optional;

@Repository
public interface WineRepository extends JpaRepository<Wine, Long> {
    Optional<Wine> findByNameAndWineTypeAndYear(String name, WineType wineType, int year);

}
