package backend.Wine_Project.model;

import jakarta.persistence.*;

import java.time.Year;
import java.util.List;


@Entity
@Table
public class Wine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "winetype_id",referencedColumnName = "id")
    private WineType wineType;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "region_id",referencedColumnName = "id")
    private Region region;
    @OneToMany(mappedBy = "wine")
    private List<GrapeVarieties> grapeVarietiesList;
    private double RatingAvg;
    private double price;
    private double alcohol;
    private Year year;

    public Wine(String name, WineType winetype, Region region, List<GrapeVarieties> grapeVarietiesList, double price, double alcohol, Year year) {
        this.name = name;
        this.wineType = winetype;
        this.region = region;
        this.grapeVarietiesList = grapeVarietiesList;
        this.price = price;
        this.alcohol = alcohol;
        this.year = year;
    }

    public Wine(Long id, String name, WineType winetype, Region region, List<GrapeVarieties> grapeVarietiesList, int ratingAvg, double price, double alcohol, Year year) {
        this.id = id;
        this.name = name;
        this.wineType = winetype;
        this.region = region;
        this.grapeVarietiesList = grapeVarietiesList;
        RatingAvg = ratingAvg;
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

    public List<GrapeVarieties> getGrapeVarietiesList() {
        return grapeVarietiesList;
    }

    public void setGrapeVarietiesList(List<GrapeVarieties> grapeVarietiesList) {
        this.grapeVarietiesList = grapeVarietiesList;
    }

    public double getRatingAvg() {
        return RatingAvg;
    }

    public void setRatingAvg(int ratingAvg) {
        RatingAvg = ratingAvg;
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

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

}
