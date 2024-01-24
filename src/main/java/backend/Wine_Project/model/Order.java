package backend.Wine_Project.model;

import jakarta.persistence.*;

@Entity
@Table(name = "wine_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Client client;
    private double totalPrice;

    public Order() {
    }

    public Order(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Order(Client client, double totalPrice) {
        this.client = client;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
