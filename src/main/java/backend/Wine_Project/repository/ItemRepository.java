package backend.Wine_Project.repository;

import backend.Wine_Project.model.Item;
import backend.Wine_Project.model.wine.Wine;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findByWineAndQuantity(Wine wine, int quantity);

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE item AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();

}
