package backend.Wine_Project.exceptions;

public class ErrorGeneratingInvoiceException extends RuntimeException {

    public ErrorGeneratingInvoiceException (String message) {
        super(message);
    }
}
