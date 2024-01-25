package backend.Wine_Project.repository;

import backend.Wine_Project.model.wine.WineType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WineTypeRepository extends JpaRepository<WineType, Long> {

    Optional<WineType> findByName(String name);
}
