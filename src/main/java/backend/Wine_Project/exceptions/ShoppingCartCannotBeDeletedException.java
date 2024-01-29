package backend.Wine_Project.exceptions;

public class ShoppingCartCannotBeDeletedException extends RuntimeException {

    public ShoppingCartCannotBeDeletedException (String message) {
        super(message);
    }
}
