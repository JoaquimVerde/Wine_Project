package backend.Wine_Project.aspects;

import backend.Wine_Project.exceptions.*;
import backend.Wine_Project.exceptions.alreadyExists.*;
import backend.Wine_Project.exceptions.notFound.*;
import backend.Wine_Project.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;


@Component
@ControllerAdvice
public class AllExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(AllExceptionHandler.class);

    @ExceptionHandler(value = {WineNotFoundException.class, ClientIdNotFoundException.class, GrapeVarietyIdNotFoundException.class,
            RegionIdNotFoundException.class, WineIdNotFoundException.class, WineTypeIdNotFoundException.class})
    public ResponseEntity<String> handleIdNotFound(Exception exception){
        logger.error(Messages.KNOWN_EXCEPTION.getMessage() + exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body((exception.getMessage()));
    }
    @ExceptionHandler(value = {ShoppingCartAlreadyBeenOrderedException.class, RatingAlreadyExistsException.class, EmailAlreadyExistsException.class,
            GrapeVarietyAlreadyExistsException.class, ItemAlreadyExistsException.class, OrderAlreadyExistsException.class, RegionAlreadyExistsException.class,
            WineAlreadyExistsException.class, WineTypeAlreadyExistsException.class})
    public ResponseEntity<String> handleObjectAlreadyExists(Exception exception){
        logger.error(Messages.KNOWN_EXCEPTION.getMessage() + exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> validationsHandlerNotValid(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(value = {YearCannotBeFutureException.class, AlreadyHaveShoppingCartToOrderException.class})
    public ResponseEntity<String> handleBadRequest(Exception exception){
        logger.error(Messages.KNOWN_EXCEPTION.getMessage() + exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }



}
