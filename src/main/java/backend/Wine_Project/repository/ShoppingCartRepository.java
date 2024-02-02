package backend.Wine_Project.repository;

import backend.Wine_Project.model.Client;
import backend.Wine_Project.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    Optional<ShoppingCart> findByClientAndOrdered(Client client, boolean ordered );
}
