package backend.Wine_Project.exceptions.alreadyExists;

public class ItemAlreadyExistsException extends RuntimeException{

    public ItemAlreadyExistsException(String message) {
        super(message);
    }
}
