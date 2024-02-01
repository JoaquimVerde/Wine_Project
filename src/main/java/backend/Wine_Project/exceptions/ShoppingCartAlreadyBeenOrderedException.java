package backend.Wine_Project.exceptions;

public class ShoppingCartAlreadyBeenOrderedException extends RuntimeException {
    public ShoppingCartAlreadyBeenOrderedException(String message) {
        super(message);
    }
}
