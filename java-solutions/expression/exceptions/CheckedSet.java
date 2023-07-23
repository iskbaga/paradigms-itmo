package expression.exceptions;


import expression.CommonInterface;

public class CheckedSet extends AbstractCheckedBinary implements CommonInterface {
    public CheckedSet(CommonInterface x, CommonInterface y) {
        super("set",x, y);
    }

    @Override
    public int calculate(int x, int y) {
        return x | (1 << y);
    }

    @Override
    public int getPriority(){
        return 2;
    }



}
