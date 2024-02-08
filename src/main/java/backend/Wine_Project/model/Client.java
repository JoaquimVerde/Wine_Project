package backend.Wine_Project.model;

import backend.Wine_Project.model.wine.Wine;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.Set;


@Entity
@Table
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(description = "Client name",type = "string",example = "João")
    private String name;
    @Schema(description = "Client email",type = "string",example = "joao@example.com")
    @Column(unique = true)
    private String email;
    @Schema(description = "Client NIF",type = "int",example = "123456789")
    @Column(unique = true)
    private int nif;
    @Schema(description = "Rated wines",type = "Set",example = "[Quinta do Crasto]")
    @ManyToMany
    private Set<Wine> ratedWines;


    public Client() {
    }

    public Client(String name, String email, int nif) {
        this.name = name;
        this.email = email;
        this.nif = nif;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public Set<Wine> getRatedWines() {
        return ratedWines;
    }

    public void setRatedWines(Set<Wine> ratedWines) {
        this.ratedWines = ratedWines;
    }
}


