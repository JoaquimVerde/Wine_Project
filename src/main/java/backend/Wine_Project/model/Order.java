package backend.Wine_Project.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "wine_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(description = "Client", type = "Client", example = "João")
    @ManyToOne
    private Client client;
    @Schema(description = "Total price", type = "double", example = "100")
    private double totalPrice;
    @Schema(description = "Shopping cart", type = "ShoppingCart", example = "ShoppingCart")
    @OneToOne
    private ShoppingCart shoppingCart;
    @Schema(description = "Invoice path", type = "string", example = "C:/Users/João/Downloads/invoice.pdf")
    @Column
    private String invoicePath;

    private boolean isPaid;

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

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public String getInvoicePath() {
        return invoicePath;
    }

    public void setInvoicePath(String invoicePath) {
        this.invoicePath = invoicePath;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }
}

