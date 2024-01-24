package backend.Wine_Project.repository;

import backend.Wine_Project.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long>  {

    @Query("SELECT AVG(rating) FROM rating WHERE wine_id = ?1")
    double getAverageRating(Long wineId);


}
