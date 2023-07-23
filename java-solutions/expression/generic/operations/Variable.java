package expression.generic.operations;


import expression.generic.calculator.Calculator;

public class Variable<T> extends AbstractOperation<T> implements CommonInterface<T> {
    private final String variable;

    public Variable(Calculator<T> calc, String x) {
        super(calc);
        this.variable = x;
    }


    @Override
    public String toString() {
        return variable;
    }

    @Override
    public String toMiniString() {
        return variable;
    }

    @Override
    public boolean equals(Object object) {
        return (this == object)
                || (object != null && this.getClass() == object.getClass()
                && this.variable.equals(((Variable<?>) object).variable));
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return switch (variable) {
            case "y" -> y;
            case "z" -> z;
            default -> x;
        };
    }

    public int getPriority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int hashCode() {
        return variable.hashCode();
    }
}
