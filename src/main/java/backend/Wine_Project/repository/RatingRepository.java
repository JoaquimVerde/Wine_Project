package backend.Wine_Project.repository;

import backend.Wine_Project.model.Client;
import backend.Wine_Project.model.Rating;
import backend.Wine_Project.model.wine.Wine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long>  {

    @Query("SELECT AVG(rate) FROM Rating WHERE wine = ?1")
    double getAverageRatingByWine(Wine wine);


    Optional<Rating> findByClientAndWine(Client client, Wine wine);

}
