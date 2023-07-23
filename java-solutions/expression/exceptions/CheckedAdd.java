package expression.exceptions;


import expression.CommonInterface;

public class CheckedAdd extends AbstractCheckedBinary implements CommonInterface {

    public CheckedAdd(CommonInterface x, CommonInterface y) {
        super("+", x, y);
    }

    @Override
    public int calculate(int x, int y) throws CalculateException {
        if (OverflowCheck.add(x, y)) throw new OverflowException(x, y, "+");
        return x + y;
    }

    @Override
    public int getPriority() {
        return 1;
    }


}