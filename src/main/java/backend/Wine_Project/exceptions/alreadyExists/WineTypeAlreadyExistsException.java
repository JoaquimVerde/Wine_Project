package backend.Wine_Project.exceptions.alreadyExists;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class WineTypeAlreadyExistsException extends RuntimeException{
    public WineTypeAlreadyExistsException(String message){
        super(message);
    }
}
