package backend.Wine_Project.exceptions;

public class CannotDeleteOrderedWineException extends RuntimeException{
    public CannotDeleteOrderedWineException(String message){ super(message);}
}
