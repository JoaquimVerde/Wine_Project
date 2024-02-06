package backend.Wine_Project.exceptions.alreadyExists;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class WineAlreadyExistsException extends RuntimeException{

    public WineAlreadyExistsException(String message){super(message);}


}
