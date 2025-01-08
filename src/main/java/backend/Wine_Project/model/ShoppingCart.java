package backend.Wine_Project.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(description = "Client", type = "Client", example = "João")
    @ManyToOne
    private Client client;
    @Schema(description = "Items", type = "Set", example = "[Quinta do Crasto]")
    @ManyToMany
    private Set<Item> items;
    @Schema(description = "Total amount", type = "double", example = "100.0")
    double totalAmount;
    @Schema(description = "Ordered", type = "boolean", example = "false")
    private boolean ordered;

    public ShoppingCart() {
    }

    public ShoppingCart(Client client, Set<Item> items) {
        this.client = client;
        this.items = new HashSet<>(items);
        for (Item item : items) {
            this.totalAmount += item.getTotalPrice();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public boolean isOrdered() {
        return ordered;
    }

    public void setOrdered(boolean ordered) {
        this.ordered = ordered;
    }


}
