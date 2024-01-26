package backend.Wine_Project.model.wine;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "region")
    private Set<Wine> wine;


    public Region(){
    }

    public Region(String name, Set<Wine> wine) {
        this.name = name;
        this.wine = wine;
    }

    public Region(String name) {
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
