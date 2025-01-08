package backend.Wine_Project.exceptions;

public class ShoppingCartCannotBeUpdatedException extends RuntimeException {

    public ShoppingCartCannotBeUpdatedException(String message) {
        super(message);
    }
}
