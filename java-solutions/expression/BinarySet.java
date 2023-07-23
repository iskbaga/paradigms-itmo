package expression;

public class BinarySet extends AbstractBinary implements CommonInterface {
    public BinarySet(CommonInterface expr1, CommonInterface expr2) {
        super("set",expr1, expr2);
    }

    @Override
    public int calculate(int a, int b) {
        return a | (1 << b);
    }
    @Override
    public double calculate(double a, double b) {
        return 0;
    }
}
