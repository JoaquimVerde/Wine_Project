package backend.Wine_Project.repository;

import backend.Wine_Project.model.Item;
import backend.Wine_Project.model.wine.Wine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findByWineAndQuantity(Wine wine, int quantity);

}
