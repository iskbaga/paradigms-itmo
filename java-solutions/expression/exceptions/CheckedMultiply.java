package expression.exceptions;


import expression.CommonInterface;

public class CheckedMultiply extends AbstractCheckedBinary implements CommonInterface {
    public CheckedMultiply(CommonInterface x, CommonInterface y) {
        super("*",x, y);
    }

    @Override
    public int calculate(int x, int y) throws CalculateException {
        if (OverflowCheck.multiply(x, y)) throw new OverflowException(x, y, "*");
        return x * y;
    }
    @Override
    public int getPriority(){
        return 0;
    }


}
