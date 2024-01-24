package backend.Wine_Project.repository;

import backend.Wine_Project.model.WineType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WineTypeRepository extends JpaRepository<WineType, Long> {
}
