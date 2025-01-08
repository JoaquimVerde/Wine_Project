package backend.Wine_Project.util;

public enum Messages {

    CLIENT_EMAIL_ALREADY_EXISTS("Client Email already exists "),
    EMAIL_FAILED_SEND("An error occurred to send the invoice email!"),

    ORDER_ALREADY_EXISTS("Order Id already exists "),

    CLIENT_ID_NOT_FOUND("Client Id Not Found. "),
    WINE_ID_NOT_FOUND("Wine Id Not Found "),


    WINE_ALREADY_EXISTS("Wine already exists! "),
    REGION_ID_NOT_FOUND("Region Id Not Found! "),
    GRAPE_VARIETY_ID_NOT_FOUND("Grape Variety Id Not Found! "),
    WINE_TYPE_ID_NOT_FOUND("Wine Type Id Not Found! "),


    ITEM_ALREADY_EXISTS("This item is already exists in database with id: "),
    ITEM_ID_NOT_FOUND("Item Id Not Found! "),
    SHOPPING_CART_NOT_FOUND("Shopping cart Id not found!"),
    SHOPPING_CART_CANNOT_BE_DELETE("This Shopping cart cannot be deleted. It's already been ordered!"),
    SHOPPING_CART_CANNOT_BE_UPDATED("This Shopping cart cannot be updated. It's already been ordered!"),

    SHOPPING_CART_ALREADY_ORDERED("This Shopping cart has already been ordered!"),
    PDF_GENERATION_ERROR("Error generating PDF"),


    KNOWN_EXCEPTION("Known exception: "),
    WINES_NOT_FOUND("Couldn't find any wines"),


    RATING_ALREADY_EXISTS("Rating already exists"),
    REGION_ALREADY_EXISTS("Region already exist, please use one region of database"),
    WINE_TYPE_ALREADY_EXISTS("Wine type already exists!"),
    GRAPE_VARIETY_CREATED("New grape variety added successfully"),
    WINE_TYPE_CREATED("New wine type added successfully"),
    REGION_CREATED("New region added successfully"),
    CLIENT_CREATED("New client added successfully"),
    CLIENT_NIF_ALREADY_EXISTS("Client nif already exists"),



    ERROR_GENERATING_INVOICE("Error generating invoice!"),

    YEAR_CANNOT_BE_FUTURE("Year cannot be future."),
    GRAPE_VARIETY_ALREADY_EXISTS("Grape variety already exists, please use one in the database"),

    ALREADY_HAVE_SHOPPING_CART_TO_ORDER("Client already have shopping cart to order."),
    WINE_WAS_ORDERED_OR_RATED("That wine was ordered or rated and cannot be deleted. Wine Id: "),
    WINE_ID_MUST_NOT_BE_LESS_THAN_1("Wine id must not be less than 1"),

    ORDER_ID_NOT_FOUND("Order ID not found!");




    private String message;

    Messages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
