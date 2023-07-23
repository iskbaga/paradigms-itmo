package expression.exceptions;

import expression.CommonInterface;

public class CheckedLog10 extends AbstractCheckedUnary implements CommonInterface {

    public CheckedLog10(CommonInterface x) {
        super("log10", x);
    }

    @Override
    public int calculate(int x) throws CalculateException {
        if (x <= 0) {
            throw new CalculateException("0");
        }
        int res = 0;
        while (x > 9) {
            x /= 10;
            res++;
        }
        return res;
    }

    @Override
    public String toMiniString() {
        if (exp instanceof AbstractCheckedBinary) {
            return "log10(" + exp.toMiniString() + ")";
        }
        return "log10 " + exp.toMiniString();
    }

    public String toString() {
        return "log10(" + exp.toString() + ")";
    }

}