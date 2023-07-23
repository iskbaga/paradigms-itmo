package expression.exceptions;
import expression.CommonInterface;

public class CheckedNegate extends AbstractCheckedUnary implements CommonInterface {
    public CheckedNegate(CommonInterface x) {
        super("-", x);
    }

    @Override
    public String toString() {
        return "-(" + exp.toString() + ")";
    }

    @Override
    public int calculate(int x) throws CalculateException {
        if (OverflowCheck.negate(x)) {
            throw new CalculateException("Overflow caused by doing operation: -(-2147483648)");
        }
        return -x;
    }

    @Override
    public double calculate(double x) {
        return -x;
    }


}
