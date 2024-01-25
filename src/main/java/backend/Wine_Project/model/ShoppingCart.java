package backend.Wine_Project.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
@Table
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    Set<Item> items;
    double totalAmount;

    public ShoppingCart() {
        this.items = new HashSet<>();
        this.totalAmount = 0;
    }

    public ShoppingCart(Set<Item> items) {
        this.items = items;
    }

    public void addToCart(Item item) {
        this.items.add(item);
    }

    public void removeFromCart(Item item) {
        Iterator<Item> it = items.iterator();

        while(it.hasNext()) {
            Item item2 = it.next();
            if (item2.getWine().equals(item.getWine())) {
                this.items.remove(item);
                break;
            }
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

    // ToDo Show Cart
    // Todo GetTotal amount
    // Todo print Invoice
}
