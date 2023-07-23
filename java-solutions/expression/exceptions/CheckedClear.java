package expression.exceptions;


import expression.CommonInterface;

public class CheckedClear extends AbstractCheckedBinary implements CommonInterface {
    public CheckedClear(CommonInterface x, CommonInterface y) {
        super("clear", x, y);
    }

    @Override
    public int calculate(int a, int b) {
        return a & (~(1 << b));
    }

    @Override
    public int getPriority() {
        return 2;
    }


}
