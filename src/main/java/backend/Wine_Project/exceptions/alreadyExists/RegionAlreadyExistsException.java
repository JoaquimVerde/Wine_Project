package backend.Wine_Project.exceptions.alreadyExists;

public class RegionAlreadyExistsException extends RuntimeException{
    public RegionAlreadyExistsException(String message) {
        super(message);
    }
}
