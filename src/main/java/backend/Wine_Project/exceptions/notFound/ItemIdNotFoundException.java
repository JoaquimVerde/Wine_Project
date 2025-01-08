package backend.Wine_Project.exceptions.notFound;

public class ItemIdNotFoundException extends RuntimeException{

    public ItemIdNotFoundException (String message) {
        super(message);
    }

}
