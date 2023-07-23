package expression;

import expression.exceptions.CalculateException;

public abstract class AbstractUnary implements CommonInterface {
    protected final String operationSymbol;
    protected final CommonInterface expr;

    public AbstractUnary(String symbol, CommonInterface expr) {
        operationSymbol = symbol;
        this.expr= expr;
    }

    @Override
    public String toString() {
        return "(" +operationSymbol + expr + ")";
    }

    @Override
    public String toMiniString() {
        System.out.println(expr.getClass());
        if(expr.getClass() == Const.class || expr.getClass() == Variable.class || expr.getClass() == Negate.class){
            return operationSymbol+" "+expr.toMiniString();
        } else{
            return operationSymbol+expr.toString();
        }
    }

    @Override
    public boolean equals(Object expr) {
        if (this == expr) return true;
        if (expr == null || expr.getClass() != this.getClass()) return false;
        return this.expr.equals(((AbstractUnary) expr).expr)
                && this.operationSymbol.equals(((AbstractUnary) expr).operationSymbol);
    }


    public int evaluate(int x, int y, int z) {
        int res;
        try {
            res = calculate(expr.evaluate(x, y, z));
            return res;
        } catch (CalculateException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public int calculate(int x) throws CalculateException{
        return 0;
    }

    public double calculate(double x) throws CalculateException{
        return 0;
    }

    @Override
    public int hashCode() {
        return ((7 * expr.hashCode() + expr.getClass().hashCode()
                + this.getClass().hashCode() + expr.getClass().hashCode()) + expr.hashCode());
    }

    public int getPriority() {
        return -1;
    }

}
