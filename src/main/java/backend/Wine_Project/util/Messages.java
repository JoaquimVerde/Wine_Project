package backend.Wine_Project.util;

public enum Messages {

    CLIENT_EMAIL_ALREADY_EXISTS("Client Email already exists "),

    ORDER_ALREADY_EXISTS("Order Id already exists "),

    CLIENT_ID_NOT_FOUND("Client Id Not Found. "),
    WINE_ID_NOT_FOUND("Wine Id Not Found "),


    WINE_ALREADY_EXISTS("Wine already exists! "),
    REGION_ID_NOT_FOUND("Region Id Not Found! "),
    GRAPE_VARIETY_ID_NOT_FOUND("Grape Variety Id Not Found! "),
    WINE_TYPE_ID_NOT_FOUND("Wine Type Id Not Found! "),


    ITEM_ALREADY_EXISTS("This item is already in shopping cart. May be you want to update the quantity! "),
    ITEM_ID_NOT_FOUND("Item Id Not Found! "),
    SHOPPING_CART_NOT_FOUND("Shopping cart Id not found!"),
    SHOPPING_CART_CANNOT_BE_DELETE("This Shopping cart cannot be deleted. It's already been ordered!"),
    SHOPPING_CART_CANNOT_BE_UPDATED("This Shopping cart cannot be updated. It's already been ordered!"),

    SHOPPING_CART_ALREADY_ORDERED("This Shopping cart has already been ordered!"),
    PDF_GENERATION_ERROR("Error generating PDF"),


    KNOWN_EXCEPTION("Known exception: "),
    WINES_NOT_FOUND("Couldn't find any wines"),


    RATING_ALREADY_EXISTS("Rating already exists"),


    ERROR_GENERATING_INVOICE("Error generating invoice!");

    YEAR_CANNOT_BE_FUTURE("Year cannot be future."),
    GRAPE_VARIETY_ALREADY_EXISTS("Grape variety already exists, please use one in the database");




    private String message;

    Messages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
