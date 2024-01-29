package backend.Wine_Project.exceptions;

public class ItemIdNotFoundException extends RuntimeException{

    public ItemIdNotFoundException (String message) {
        super(message);
    }

}
