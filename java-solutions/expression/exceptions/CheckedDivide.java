package expression.exceptions;

import expression.CommonInterface;

public class CheckedDivide extends AbstractCheckedBinary implements CommonInterface {
    public CheckedDivide(CommonInterface x, CommonInterface y) {
        super("/", x, y);
    }

    @Override
    public int calculate(int x, int y) throws CalculateException {
        if (y == 0) throw new DivisionByZeroException();
        if (OverflowCheck.divide(x, y)) throw new OverflowException(x, y, "/");
        return x / y;
    }

    @Override
    public int getPriority() {
        return 0;
    }



}
