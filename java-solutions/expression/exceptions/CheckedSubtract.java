package expression.exceptions;


import expression.CommonInterface;

public class CheckedSubtract extends AbstractCheckedBinary implements CommonInterface {
    public CheckedSubtract(CommonInterface x, CommonInterface y) {
        super("-", x, y);
    }

    @Override
    public int calculate(int a, int b) throws CalculateException {
        if (OverflowCheck.subtract(a, b)){
            throw new OverflowException(a, b, "-");
        }
        return a - b;
    }

    @Override
    public int getPriority() {
        return 1;
    }



}
