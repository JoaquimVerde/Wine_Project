package backend.Wine_Project.model;

import backend.Wine_Project.model.wine.Wine;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(description = "Client",type = "Client",example = "John Doe")
    @ManyToOne
    private Client client;
    @Schema(description = "Wine",type = "Wine",example = "Quinta do Crasto")
    @ManyToOne
    private Wine wine;
    @Schema(description = "Rating",type = "double",example = "4.5")
    private double rate;
    @Schema(description = "Review",type = "string",example = "Great wine")
    @Size(max = 10000)
    private String review;



    public Rating() {
    }

    public Rating(Client client, Wine wine, double rating) {
        this.client = client;
        this.wine = wine;
        this.rate = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Wine getWine() {
        return wine;
    }

    public void setWine(Wine wine) {
        this.wine = wine;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
