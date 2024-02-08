package backend.Wine_Project.model.wine;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table
public class WineType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(description = "Wine type name", type = "string", example = "Red")
    private String name;
    @Schema(description = "Wine", type = "Set", example = "[Quinta do Crasto]")
    @OneToMany(mappedBy = "wineType")
    private Set<Wine> wine;


    public WineType() {
    }

    public WineType(String name, Set<Wine> wine) {
        this.name = name;
        this.wine = wine;
    }

    public WineType(String name) {
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
