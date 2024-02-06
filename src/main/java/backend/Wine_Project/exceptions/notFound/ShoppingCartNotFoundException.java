package backend.Wine_Project.exceptions.notFound;

public class ShoppingCartNotFoundException extends RuntimeException {

    public ShoppingCartNotFoundException (String message) {
        super(message);
    }
}
