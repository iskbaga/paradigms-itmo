package expression;

public class Count extends AbstractUnary implements CommonInterface {

    public Count(CommonInterface x) {
        super("count", x);
    }

    @Override
    public int calculate(int x) {
        return Integer.bitCount(x);
    }

}