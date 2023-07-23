package expression;
import expression.exceptions.AbstractCheckedBinary;

public class Subtract extends AbstractBinary implements CommonInterface {
    public Subtract(CommonInterface x, CommonInterface y) {
        super("-", x, y);
    }

    @Override
    public int calculate(int x, int y) {
        return x - y;
    }

    @Override
    public double calculate(double x, double y) {
        return x - y;
    }

}