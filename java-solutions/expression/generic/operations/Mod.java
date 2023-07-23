package expression.generic.operations;

public class Mod<T> extends AbstractBinary<T> implements CommonInterface<T> {
    public Mod(CommonInterface<T> x, CommonInterface<T> y) {
        super("mod", x, y);
    }

    @Override
    public T calculate(T x, T y) {
        return calculator.mod(x, y);
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
