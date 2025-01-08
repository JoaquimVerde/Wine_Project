package backend.Wine_Project.exceptions.alreadyExists;

public class NifAlreadyExistsException extends RuntimeException{
    public NifAlreadyExistsException(String message){
        super(message);
    }
}
