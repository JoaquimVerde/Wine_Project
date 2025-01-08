package backend.Wine_Project.repository;

import backend.Wine_Project.model.wine.Wine;
import backend.Wine_Project.model.wine.WineType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface WineTypeRepository extends JpaRepository<WineType, Long> {

    Optional<WineType> findByName(String name);

    @Query("SELECT s.wine FROM WineType s WHERE s.id = ?1")
    public Set<Wine> findWinesByType(Long id);

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE wine_type AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
}
