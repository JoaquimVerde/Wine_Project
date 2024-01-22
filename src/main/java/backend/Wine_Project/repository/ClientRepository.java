package backend.Wine_Project.repository;

import backend.Wine_Project.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {


}
