package backend.Wine_Project.model.wine;

import backend.Wine_Project.model.wine.Wine;
import jakarta.persistence.*;

@Entity
@Table
public class GrapeVarieties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private Wine wine;

    public GrapeVarieties() {
    }

    public GrapeVarieties(String name, Wine wine) {
        this.name = name;
        this.wine = wine;
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

    public Wine getWine() {
        return wine;
    }

    public void setWine(Wine wine) {
        this.wine = wine;
    }
}
