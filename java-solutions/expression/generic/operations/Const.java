package expression.generic.operations;
import expression.generic.calculator.Calculator;

import java.util.Objects;

public class Const<T> extends AbstractOperation<T> implements CommonInterface<T> {
    private final T current;

    public Const(Calculator<T> calculator, String x) {
        super(calculator);
        current = calculator.parse(x);
    }

    @Override
    public Calculator<T> getCalc() {
        return calculator;
    }

    public int getPriority() {
        return -1;
    }

    public T evaluate(T x) {
        return current;
    }

    @Override
    public String toMiniString() {
        return this.toString();
    }

    public String toString() {
        return current + "";
    }

    @Override
    public boolean equals(Object exp) {
        return exp != null && this.getClass() == exp.getClass()
                && (Objects.equals(this.current, ((Const<?>) exp).current));
    }

    @Override
    public int hashCode() {
        return current.hashCode();
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return current;
    }
}
