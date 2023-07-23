package expression.generic.operations;



public class Divide<T> extends AbstractBinary<T> implements CommonInterface<T> {
    public Divide(CommonInterface<T> x, CommonInterface<T> y) {
        super("/", x, y);
    }

    @Override
    public T calculate(T x, T y) {
        return calculator.divide(x, y);
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
