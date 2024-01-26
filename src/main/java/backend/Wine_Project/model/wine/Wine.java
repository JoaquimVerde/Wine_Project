package backend.Wine_Project.model.wine;

import jakarta.persistence.*;

import java.time.Year;
import java.util.List;
import java.util.Set;


@Entity
@Table
public class Wine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private WineType wineType;
    @ManyToOne
    private Region region;
    @ManyToMany
    @JoinTable(
            name = "wine_grapeVariety",
            joinColumns = @JoinColumn(name = "wine_id"),
            inverseJoinColumns = @JoinColumn(name = "grapeVariety_id"))
    private Set<GrapeVarieties> grapeVarietiesList;
    private double ratingAvg;
    private double price;
    private double alcohol;
    private int year;

    public Wine(String name, WineType winetype, Region region, double price, double alcohol, int year) {
        this.name = name;
        this.wineType = winetype;
        this.region = region;
        this.price = price;
        this.alcohol = alcohol;
        this.year = year;
    }

    public Wine(Long id, String name, WineType winetype, Region region, Set<GrapeVarieties> grapeVarietiesList, int ratingAvg, double price, double alcohol, int year) {
        this.id = id;
        this.name = name;
        this.wineType = winetype;
        this.region = region;
        this.grapeVarietiesList = grapeVarietiesList;
        this.ratingAvg = ratingAvg;
        this.price = price;
        this.alcohol = alcohol;
        this.year = year;
    }

    public Wine() {
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

    public WineType getWineType() {
        return wineType;
    }

    public void setWineType(WineType wineType) {
        this.wineType = wineType;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Set<GrapeVarieties> getGrapeVarietiesList() {
        return grapeVarietiesList;
    }

    public void setGrapeVarietiesList(Set<GrapeVarieties> grapeVarietiesList) {
        this.grapeVarietiesList = grapeVarietiesList;
    }

    public double getRatingAvg() {
        return ratingAvg;
    }

    public void setRatingAvg(double ratingAvg) {
        this.ratingAvg = ratingAvg;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(double alcohol) {
        this.alcohol = alcohol;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
