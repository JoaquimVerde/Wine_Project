package backend.Wine_Project.exceptions;

public class ShoppingCartNotFoundException extends RuntimeException {

    public ShoppingCartNotFoundException (String message) {
        super(message);
    }
}
