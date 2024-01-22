package backend.Wine_Project.model;

import ch.qos.logback.core.net.server.Client;
import jakarta.persistence.*;

@Entity
@Table
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Client client;
    private double totalCost;
    private Wine wine;
    private int numberOfBottles;

    public Order() {
    }

    public Order(Client client, double totalCost, Wine wine, int numberOfBottles) {
        this.client = client;
        this.totalCost = totalCost;
        this.wine = wine;
        this.numberOfBottles = numberOfBottles;
    }

    public double getTotalCost(Wine wine, int numberOfBottles) {
        totalCost = wine.getPrice() * numberOfBottles;
        return totalCost;
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



    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public Wine getWine() {
        return wine;
    }

    public void setWine(Wine wine) {
        this.wine = wine;
    }

    public int getNumberOfBottles() {
        return numberOfBottles;
    }

    public void setNumberOfBottles(int numberOfBottles) {
        this.numberOfBottles = numberOfBottles;
    }


}
