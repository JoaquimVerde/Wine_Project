package backend.Wine_Project.repository;

import backend.Wine_Project.model.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE wine_order AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();

}
