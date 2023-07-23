package expression.exceptions;

public class OverflowException extends CalculateException {
    public OverflowException(int x, int y, String operation) {
        super("Overflow by " + x + " " + operation + " " + y);
    }
}
