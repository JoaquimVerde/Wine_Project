package backend.Wine_Project.exceptions;

public class OrderAlreadyExistsException extends RuntimeException{

    public OrderAlreadyExistsException(String message) {
        super(message);
    }
}
