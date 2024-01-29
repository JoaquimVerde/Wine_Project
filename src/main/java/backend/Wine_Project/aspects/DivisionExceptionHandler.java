package backend.Wine_Project.aspects;

import backend.Wine_Project.exceptions.*;
import backend.Wine_Project.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Component
@ControllerAdvice
public class DivisionExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(DivisionExceptionHandler.class);

    @ExceptionHandler(value = {ClientIdNotFoundException.class, GrapeVarietyIdNotFoundException.class, RegionIdNotFoundException.class, WineIdNotFoundException.class, WineTypeIdNotFoundException.class})
    public ResponseEntity<String> handleIdNotFound(Exception exception){
        logger.error(Messages.KNOWN_EXCEPTION.getMessage() + exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body((exception.getMessage()));
    }
    @ExceptionHandler(value = {RatingAlreadyExistsException.class, EmailAlreadyExistsException.class, GrapeVarietyAlreadyExistsException.class, ItemAlreadyExistsException.class, OrderAlreadyExistsException.class, RegionAlreadyExistsException.class, WineAlreadyExistsException.class, WineTypeAlreadyExistsException.class})
    public ResponseEntity<String> handleObjectAlreadyExists(Exception exception){
        logger.error(Messages.KNOWN_EXCEPTION.getMessage() + exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }



}
