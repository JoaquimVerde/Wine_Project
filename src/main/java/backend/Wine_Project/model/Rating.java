package backend.Wine_Project.model;

import backend.Wine_Project.model.wine.Wine;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Wine wine;
    private double rate;
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
