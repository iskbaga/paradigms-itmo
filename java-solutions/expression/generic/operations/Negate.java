package expression.generic.operations;


public class Negate<T> extends AbstractUnary<T> implements CommonInterface<T> {
    public Negate(CommonInterface<T> x) {
        super("-", x);
    }

    @Override
    public String toString() {
        return "-(" + exp.toString() + ")";
    }

    @Override
    public T calculate(T x) {
        return calculator.negate(x);
    }

}
