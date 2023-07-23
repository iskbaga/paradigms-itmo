package expression.exceptions;

import expression.CommonInterface;
import expression.Const;
import expression.Variable;


public abstract class AbstractCheckedUnary implements CommonInterface {
    protected final String operationSymbol;
    protected final CommonInterface exp;

    public AbstractCheckedUnary(String symbol, CommonInterface exp) {
        operationSymbol = symbol;
        this.exp = exp;
    }

    @Override
    public String toString() {
        if(operationSymbol.equals("count")){
            return operationSymbol + "(" + exp.toString() + ")";
        }
        return "(" + operationSymbol + exp + ")";
    }


    @Override
    public String toMiniString() {
        if (exp instanceof AbstractCheckedUnary || exp.getClass() == Const.class || exp.getClass() == Variable.class) {
            return operationSymbol + " " + exp.toMiniString();
        }
        return operationSymbol + "(" + exp.toMiniString() + ")";
    }

    @Override
    public boolean equals(Object expr) {
        if (this == expr) return true;
        if (expr == null || expr.getClass() != this.getClass()) return false;
        return this.exp.equals(((AbstractCheckedUnary) expr).exp)
                && this.operationSymbol.equals(((AbstractCheckedUnary) expr).operationSymbol);
    }





    public int evaluate(int x, int y, int z) {
        int res;
        try {
            res = calculate(exp.evaluate(x, y, z));
            return res;
        } catch (CalculateException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public int calculate(int x) throws CalculateException {
        return 0;
    }

    public double calculate(double x) throws CalculateException {
        return 0;
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
