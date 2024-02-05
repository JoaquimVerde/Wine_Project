package backend.Wine_Project.exceptions.notFound;

public class WineNotFoundException extends RuntimeException{
    public WineNotFoundException(String message){
        super(message);
    }
}
