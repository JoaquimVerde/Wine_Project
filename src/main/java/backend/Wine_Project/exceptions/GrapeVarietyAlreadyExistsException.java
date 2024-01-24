package backend.Wine_Project.exceptions;

public class GrapeVarietyAlreadyExistsException extends RuntimeException{

    public GrapeVarietyAlreadyExistsException(String message) {
        super(message);
    }
}
