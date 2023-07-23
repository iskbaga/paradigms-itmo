package expression.generic.operations;


public class Subtract<T> extends AbstractBinary<T> implements CommonInterface<T> {
    public Subtract(CommonInterface<T> x, CommonInterface<T> y) {
        super("-", x, y);
    }

    @Override
    public T calculate(T a, T b) {
        return calculator.subtract(a, b);
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
