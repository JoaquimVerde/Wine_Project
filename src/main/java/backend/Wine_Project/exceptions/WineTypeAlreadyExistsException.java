package backend.Wine_Project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class WineTypeAlreadyExistsException extends RuntimeException{
    public WineTypeAlreadyExistsException(String message){
        super(message);
    }
}
