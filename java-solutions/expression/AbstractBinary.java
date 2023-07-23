package expression;

import expression.exceptions.*;

import java.util.Objects;

public abstract class AbstractBinary implements CommonInterface {
    private final String operationSymbol;
    private final CommonInterface leftPart;
    private final CommonInterface rightPart;

    public AbstractBinary(String symbol, CommonInterface leftPart, CommonInterface rightPart) {
        operationSymbol = symbol;
        this.leftPart = leftPart;
        this.rightPart = rightPart;
    }

    @Override
    public String toString() {
        return "(" + leftPart.toString() + " " + operationSymbol + " " + rightPart.toString() + ")";
    }

    private void func(StringBuilder sb, CommonInterface expression, boolean right) {
        if (expression.getClass() == Divide.class && ((right && (operationSymbol.equals("/") || operationSymbol.equals("*"))))
                || (expression.getClass() == Multiply.class && operationSymbol.equals("/") && right)
                || (expression.getClass() == Subtract.class && operationSymbol.equals("/"))
                || ((expression.getClass() == Subtract.class || expression.getClass() == Add.class)
                && (operationSymbol.equals("/") || operationSymbol.equals("*") || (operationSymbol.equals("-") && right)))) {
            sb.append("(");
            sb.append(expression.toMiniString());
            sb.append(")");
        } else if(right && (expression.getClass() == BinarySet.class || expression.getClass() == BinaryClear.class)){
            sb.append("(");
            sb.append(expression.toMiniString());
            sb.append(")");
        } else if(!right &&
                (expression.getClass() == BinarySet.class || expression.getClass() == BinaryClear.class)
                && !((operationSymbol.equals("set") || operationSymbol.equals("clear")))){
            sb.append("(");
            sb.append(expression.toMiniString());
            sb.append(")");
        } else{
            sb.append(expression.toMiniString());
        }
    }

    @Override
    public String toMiniString() {
        StringBuilder sb = new StringBuilder();
        func(sb, leftPart, false);
        sb.append(" ").append(operationSymbol).append(" ");
        func(sb, rightPart, true);
        return sb.toString();
    }

    @Override
    public boolean equals(Object exp) {
        if (exp == null) {
            return false;
        } else if (this == exp) {
            return true;
        } else if (exp.getClass() != this.getClass()) {
            return false;
        }
        return leftPart.equals(((AbstractBinary) exp).leftPart)
                && rightPart.equals(((AbstractBinary) exp).rightPart)
                && Objects.equals(operationSymbol, ((AbstractBinary) exp).operationSymbol);
    }

    public int evaluate(int x, int y, int z) {
        int res;
        try {
            res = calculate(leftPart.evaluate(x, y, z), rightPart.evaluate(x, y, z));
            return res;
        } catch (CalculateException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    public int calculate(int x, int y) throws CalculateException{
        return 0;
    }

    public double calculate(double x, double y) throws CalculateException{
        return 0;
    }

    public int getPriority(){
        return 0;
    }

    @Override
    public int hashCode() {
        return ((7 * leftPart.hashCode() + rightPart.getClass().hashCode()
                + this.getClass().hashCode() + leftPart.getClass().hashCode()) + rightPart.hashCode());
    }
}