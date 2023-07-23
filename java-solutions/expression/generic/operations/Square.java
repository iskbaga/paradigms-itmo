package expression.generic.operations;

public class Square<T> extends AbstractUnary<T> implements CommonInterface<T> {
    public Square(CommonInterface<T> x) {
        super("square", x);
    }

    @Override
    public String toMiniString() {
        if (exp instanceof AbstractBinary) {
            return "square(" + exp.toMiniString() + ")";
        }
        return "square " + exp.toMiniString();
    }

    public String toString() {
        return "square(" + exp.toString() + ")";
    }

    @Override
    public T calculate(T x) {
        return calculator.square(x);
    }

}
