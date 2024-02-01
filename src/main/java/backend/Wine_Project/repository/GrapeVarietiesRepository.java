package backend.Wine_Project.repository;

import backend.Wine_Project.model.wine.GrapeVarieties;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GrapeVarietiesRepository extends JpaRepository<GrapeVarieties, Long> {

    Optional<GrapeVarieties> findGrapeVarietiesByName(String name);
    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE grape_varieties AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
}
