package backend.Wine_Project.repository;

import backend.Wine_Project.model.Region;
import backend.Wine_Project.regionDto.RegionDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region,Long> {
    Optional<Region> findByName(RegionDto modelCreateDto);
}
