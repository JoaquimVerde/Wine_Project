package backend.Wine_Project.repository;

import backend.Wine_Project.model.Wine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WineRepository extends JpaRepository<Wine, Long> {
}
