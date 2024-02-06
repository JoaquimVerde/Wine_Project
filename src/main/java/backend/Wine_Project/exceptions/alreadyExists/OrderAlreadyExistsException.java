package backend.Wine_Project.exceptions.alreadyExists;

public class OrderAlreadyExistsException extends RuntimeException{

    public OrderAlreadyExistsException(String message) {
        super(message);
    }
}
