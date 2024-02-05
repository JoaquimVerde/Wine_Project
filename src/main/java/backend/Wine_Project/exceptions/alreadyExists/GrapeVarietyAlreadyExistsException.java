package backend.Wine_Project.exceptions.alreadyExists;

public class GrapeVarietyAlreadyExistsException extends RuntimeException{

    public GrapeVarietyAlreadyExistsException(String message) {
        super(message);
    }
}
