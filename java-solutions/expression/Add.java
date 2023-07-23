package expression;

public class Add extends AbstractBinary implements CommonInterface {

    public Add(CommonInterface x, CommonInterface y) {
        super("+", x, y);
    }

    @Override
    public int calculate(int x, int y) {
        return x + y;
    }
    @Override
    public double calculate(double x, double y) {
        return x + y;
    }

}