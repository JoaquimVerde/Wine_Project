package backend.Wine_Project.exceptions;

public class YearCannotBeFutureException extends RuntimeException{

    public YearCannotBeFutureException (String message){ super(message);}
}
