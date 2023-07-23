package expression.exceptions;

public class ParseException extends Exception {

    public ParseException(String message, int pos) {
        super(message + " at the pos: " + pos);
    }
}
