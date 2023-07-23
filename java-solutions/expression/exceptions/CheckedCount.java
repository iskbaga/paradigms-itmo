package expression.exceptions;
import expression.CommonInterface;

public class CheckedCount extends AbstractCheckedUnary implements CommonInterface {

    public CheckedCount(CommonInterface x) {
        super("count", x);
    }

    @Override
    public int calculate(int x) {
        return Integer.bitCount(x);
    }
}