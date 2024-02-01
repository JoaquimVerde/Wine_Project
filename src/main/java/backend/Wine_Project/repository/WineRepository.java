package backend.Wine_Project.repository;

import backend.Wine_Project.dto.wineDto.WineReadDto;
import backend.Wine_Project.model.wine.Wine;
import backend.Wine_Project.model.wine.WineType;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface WineRepository extends JpaRepository<Wine, Long> {
    Optional<Wine> findByNameAndWineTypeAndYear(String name, WineType wineType, int year);



    List<Wine> findByNameAndYear(String name, int year);
    

    List<Wine> findByName(String name);

    List<Wine> findByYear(int year);
    

    List<Wine> findByWineType(WineType wineType1);

    List<Wine> findWinesByNameAndYearAndWineTypeId(String name, int year, Long wineTypeId);

    List<Wine> findByNameAndWineTypeId(String name, Long wineTypeId);

    List<Wine> findByYearAndWineTypeId(int year, Long wineTypeId);

    List<Wine> findByWineTypeId(Long wineTypeId);

    Page<Wine> findAll(Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE wine AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
}
