package expression.generic.operations;


public class Add<T> extends AbstractBinary<T> implements CommonInterface<T> {

    public Add(CommonInterface<T> x, CommonInterface<T> y) {
        super("+", x, y);
    }

    @Override
    public T calculate(T x, T y) {
        return calculator.add(x, y);
    }

    @Override
    public int getPriority() {
        return 1;
    }

}