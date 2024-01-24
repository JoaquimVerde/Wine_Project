package backend.Wine_Project.exceptions;

public class OrderALreadyExistsException extends RuntimeException{

    public OrderALreadyExistsException (String message) {
        super(message);
    }
}
