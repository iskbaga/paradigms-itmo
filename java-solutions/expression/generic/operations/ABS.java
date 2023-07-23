package expression.generic.operations;

public class ABS<T> extends AbstractUnary<T> implements CommonInterface<T> {
    public ABS(CommonInterface<T> x) {
        super("abs", x);
    }

    @Override
    public String toMiniString() {
        if (exp instanceof AbstractBinary<T>) {
            return "abs(" + exp.toMiniString() + ")";
        }
        return "abs " + exp.toMiniString();
    }

    public String toString() {
        return "abs(" + exp.toString() + ")";
    }

    @Override
    public T calculate(T x) {
        return calculator.abs(x);
    }

}
