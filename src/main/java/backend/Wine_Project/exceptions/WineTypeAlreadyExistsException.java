package backend.Wine_Project.exceptions;

public class WineTypeAlreadyExistsException extends RuntimeException{
    public WineTypeAlreadyExistsException(String message){
        super(message);
    }
}
