package backend.Wine_Project.exceptions;

public class WineNotFoundException extends RuntimeException{
    public WineNotFoundException(String message){
        super(message);
    }
}
