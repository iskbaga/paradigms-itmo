package expression.exceptions;

import expression.CommonInterface;

public class CheckedPow10 extends AbstractCheckedUnary implements CommonInterface {

    public CheckedPow10(CommonInterface x) {
        super("pow10", x);
    }

    @Override
    public int calculate(int x) throws CalculateException {
        int a = 10;
        int ans = 1;
        if (x < 0 || x > 9) {
            throw new CalculateException("0");
        }
        while (x > 0) {
            if (x % 2 == 1) {
                ans = ans * a;
            }
            a = a * a;
            x = x / 2;
        }
        return ans;
    }

    @Override
    public String toMiniString() {
        if (exp instanceof AbstractCheckedBinary) {
            return "pow10(" + exp.toMiniString() + ")";
        }
        return "pow10 " + exp.toMiniString();
    }

    public String toString() {
        return "pow10(" + exp.toString() + ")";
    }

}