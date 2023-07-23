package expression;

public class Negate extends AbstractUnary implements CommonInterface {

    public Negate(CommonInterface x) {
        super("-", x);
    }

    @Override
    public double calculate(double x) {
        return -x;
    }

    @Override
    public int calculate(int x) {
        return -x;
    }


}