package backend.Wine_Project.model.wine;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.util.Set;


@Entity
@Table
public class Wine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(description = "Wine name", type = "string", example = "Quinta do Crasto")
    private String name;
    @Schema(description = "Wine type", type = "WineType", example = "Red")
    @ManyToOne
    private WineType wineType;
    @Schema(description = "Region", type = "Region", example = "Douro")
    @ManyToOne
    private Region region;
    @Schema(description = "Grape varieties", type = "Set", example = "[Touriga Nacional, Tinta Roriz]")
    @ManyToMany
    @JoinTable(
            name = "wine_grapeVariety",
            joinColumns = @JoinColumn(name = "wine_id"),
            inverseJoinColumns = @JoinColumn(name = "grapeVariety_id"))
    private Set<GrapeVarieties> grapeVarietiesList;
    @Schema(description = "Rating average", type = "double", example = "4.5")
    private double ratingAvg;
    @Schema(description = "Price", type = "double", example = "20.0")
    private double price;
    @Schema(description = "Alcohol", type = "double", example = "13.5")
    private double alcohol;
    @Schema(description = "Year", type = "int", example = "2015")
    private int year;
    @Schema(description = "Is rated", type = "boolean", example = "true")
    private boolean isRated;
    @Schema(description = "Is item", type = "boolean", example = "true")
    private boolean isItem;

    public Wine(String name, WineType winetype, Region region, double price, double alcohol, int year, Set<GrapeVarieties> grapeVarietiesList) {
        this.name = name;
        this.wineType = winetype;
        this.region = region;
        this.price = price;
        this.alcohol = alcohol;
        this.year = year;
        this.grapeVarietiesList = grapeVarietiesList;
    }

    public Wine(Long id, String name, WineType winetype, Region region, Set<GrapeVarieties> grapeVarietiesList, double ratingAvg, double price, double alcohol, int year) {
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

    public boolean isRated() {
        return isRated;
    }

    public void setRated(boolean rated) {
        isRated = rated;
    }

    public boolean isItem() {
        return isItem;
    }

    public void setItem(boolean item) {
        isItem = item;
    }
}
