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
    @Lob
    @Column(length = 1048576)
    private byte[] pdfContent;

    public Order() {
    }


    public Order(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
        this.totalPrice = shoppingCart.getTotalAmount();
        this.client = shoppingCart.getClient();


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

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }


    public byte[] getPdfContent() {
        return pdfContent;
    }

    public void setPdfContent(byte[] pdfContent) {
        this.pdfContent = pdfContent;
    }

