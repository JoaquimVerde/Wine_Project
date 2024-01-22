package backend.Wine_Project.repository;

import backend.Wine_Project.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long>  {
}
