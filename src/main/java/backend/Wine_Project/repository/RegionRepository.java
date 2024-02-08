package backend.Wine_Project.repository;

import backend.Wine_Project.model.wine.Region;
import backend.Wine_Project.model.wine.Wine;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface RegionRepository extends JpaRepository<Region, Long> {

    Optional<Region> findByName(String name);

    @Query("SELECT s.wine FROM Region s WHERE s.id = ?1")
    public Set<Wine> findWinesByRegion(Long id);

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE region AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();


}
