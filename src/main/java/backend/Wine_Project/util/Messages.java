package backend.Wine_Project.util;

public enum Messages {

    CLIENT_EMAIL_ALREADY_EXISTS("Client Email already exists"),

    ORDER_ALREADY_EXISTS("Order Id already exists"),

    CLIENT_ID_NOT_FOUND("Client Id Not Found."),
    WINE_ID_NOT_FOUND("Wine Id Not Found"),

    WINE_ALREADY_EXISTS("Wine already exists!"),
    REGION_ID_NOT_FOUND("Region Id Not Found!"),
    GRAPE_VARIETY_ID_NOT_FOUND("Grape Variety Id Not Found!"),
    WINE_TYPE_ID_NOT_FOUND("Wine Type Id Not Found!");




    private String message;

    Messages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
