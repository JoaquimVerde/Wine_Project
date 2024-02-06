package backend.Wine_Project.repository;

import backend.Wine_Project.model.Client;
import backend.Wine_Project.model.ShoppingCart;
import backend.Wine_Project.model.wine.Wine;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    Optional<ShoppingCart> findByClientAndOrdered(Client client, boolean ordered );

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE shopping_cart AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();

}
