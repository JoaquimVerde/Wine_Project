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
    @OneToOne
    private ShoppingCart shoppingCart;

    @Column
    private String invoicePath;

    public Order() {
    }


    public Order(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
        this.totalPrice = shoppingCart.getTotalAmount();
        this.client = shoppingCart.getClient();
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public String getInvoicePath() {
        return invoicePath;
    }

    public void setInvoicePath(String invoicePath) {
        this.invoicePath = invoicePath;
    }
}

