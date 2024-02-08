package backend.Wine_Project.exceptions;

public class EmailSendFiledException extends RuntimeException{

    public EmailSendFiledException (String message) {
        super(message);
    }
}
