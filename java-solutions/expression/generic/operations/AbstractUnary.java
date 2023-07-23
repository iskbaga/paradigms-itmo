package expression.generic.operations;

public abstract class AbstractUnary<T> extends AbstractOperation<T> implements CommonInterface<T> {
    protected final String operationSymbol;
    protected final CommonInterface<T> exp;

    public AbstractUnary(String symbol, CommonInterface<T> exp) {
        super(exp.getCalc());
        operationSymbol = symbol;
        this.exp = exp;
    }

    @Override
    public String toString() {
        if (operationSymbol.equals("count")) {
            return operationSymbol + "(" + exp.toString() + ")";
        }
        return "(" + operationSymbol + exp + ")";
    }


    @Override
    public String toMiniString() {
        if (exp instanceof AbstractUnary<T> || exp.getClass() == Const.class || exp.getClass() == Variable.class) {
            return operationSymbol + " " + exp.toMiniString();
        }
        return operationSymbol + "(" + exp.toMiniString() + ")";
    }

    @Override
    public boolean equals(Object expr) {
        if (this == expr) return true;
        if (expr == null || expr.getClass() != this.getClass()) return false;
        return this.exp.equals(((AbstractUnary<T>) expr).exp)
                && this.operationSymbol.equals(((AbstractUnary<T>) expr).operationSymbol);
    }


    public T evaluate(T x, T y, T z) {
        return calculate(exp.evaluate(x, y, z));
    }

    public T calculate(T x) {
        return null;
    }


    public int getPriority() {
        return -1;
    }

    @Override
    public int hashCode() {
        return ((7 * exp.hashCode() + exp.getClass().hashCode()
                + this.getClass().hashCode() + exp.getClass().hashCode()) + exp.hashCode());
    }

}
