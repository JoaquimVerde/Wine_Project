package backend.Wine_Project.model.wine;

import backend.Wine_Project.model.wine.Wine;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table
public class GrapeVarieties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "grapeVarietiesList")
    private Set<Wine> wine;

    public GrapeVarieties() {
    }

    public GrapeVarieties(String name, Set<Wine> wine) {
        this.name = name;
        this.wine = wine;
    }

    public GrapeVarieties(String name) {
        this.name = name;
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

    public Set<Wine> getWine() {
        return wine;
    }

    public void setWine(Set<Wine> wine) {
        this.wine = wine;
    }
}
