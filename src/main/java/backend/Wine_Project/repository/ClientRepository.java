package backend.Wine_Project.repository;

import backend.Wine_Project.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {


    Optional<Client> findClientByEmail(String email);
}
