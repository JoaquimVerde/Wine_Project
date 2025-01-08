package backend.Wine_Project.model;

import backend.Wine_Project.model.wine.Wine;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(description = "Wine", type = "Wine", example = "Quinta do Crasto")
    @OneToOne(cascade = CascadeType.ALL)
    private Wine wine;
    @Schema(description = "Quantity", type = "int", example = "2")
    private int quantity;
    @Schema(description = "Total price", type = "double", example = "20.0")
    private double totalPrice;

    public Item() {
    }

    public Item(Wine wine, int quantity) {
        this.wine = wine;
        this.quantity = quantity;
        this.totalPrice = wine.getPrice() * quantity;
    }

    public Long getId() {
        return id;
    }

    public Wine getWine() {
        return wine;
    }

    public void setWine(Wine wine) {
        this.wine = wine;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String toString() {
        String itemString = this.wine.getName() + ": ";
        itemString = itemString + this.quantity + "\n";
        return itemString;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
