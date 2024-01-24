package backend.Wine_Project.util;

public enum Messages {

    CLIENT_EMAIL_ALREADY_EXISTS("Client Email already exists"),

    ORDER_ALREADY_EXISTS("Order Id already exists"),

    CLIENT_ID_NOT_FOUND("Client Id Not Found."),
    WINE_ID_NOT_FOUND("Wine Id Not Found");



    private String message;

    Messages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
