package backend.Wine_Project.model.wine;

import backend.Wine_Project.model.wine.Wine;
import jakarta.persistence.*;

@Entity
@Table
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne(mappedBy = "region")
    private Wine wine;


    public Region(){
    }

    public Region(String name, Wine wine) {
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
