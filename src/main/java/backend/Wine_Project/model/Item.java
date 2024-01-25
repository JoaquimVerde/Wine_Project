package backend.Wine_Project.model;

import backend.Wine_Project.model.wine.Wine;
import jakarta.persistence.*;

@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    private Wine wine;
    private int quantity;
    private double itemTotalAmount;


    public Item (Wine wine, int quantity) {
        this.wine = wine;
        this.quantity = quantity;
        this.itemTotalAmount = wine.getPrice() * quantity;
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

    public double getItemTotalAmount() {
        return itemTotalAmount;
    }

    public void setItemTotalAmount(double itemTotalAmount) {
        this.itemTotalAmount = itemTotalAmount;
    }

    public String toString() {
        String itemString = this.wine.getName() + ": ";
        itemString = itemString + this.quantity + "\n";
        return itemString;
    }
}
