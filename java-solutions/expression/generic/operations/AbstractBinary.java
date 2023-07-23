package expression.generic.operations;


import expression.generic.calculator.Calculator;

import java.util.HashMap;

public abstract class AbstractBinary<T> extends AbstractOperation<T> implements CommonInterface<T> {
    private final String operationSymbol;
    private final CommonInterface<T> leftPart;
    private final CommonInterface<T> rightPart;
    private final HashMap<String, Boolean> associativity = new HashMap<>() {{
        put("*", true);
        put("/", false);
        put("+", true);
        put("-", false);
        put("mod", false);
    }};
    private final HashMap<String, Integer> priority = new HashMap<>() {{
        put("*", 0);
        put("mod", 0);
        put("/", 0);
        put("+", 1);
        put("-", 1);
    }};

    public AbstractBinary(String symbol, CommonInterface<T> leftPart, CommonInterface<T> rightPart) {
        super(rightPart.getCalc());
        operationSymbol = symbol;
        this.leftPart = leftPart;
        this.rightPart = rightPart;
    }

    @Override
    public String toString() {
        return "(" + leftPart.toString() + " " + operationSymbol + " " + rightPart.toString() + ")";
    }

    private void func(StringBuilder sb, CommonInterface<T> expression, boolean right) {
        if (expression instanceof AbstractUnary || expression.getClass() == Const.class || expression.getClass() == Variable.class) {
            sb.append(expression.toMiniString());
        } else if (right) {
            if (expression.getPriority() > priority.get(operationSymbol)) {
                sb.append("(");
                sb.append(expression.toMiniString());
                sb.append(")");
            } else if (expression.getPriority() == priority.get(operationSymbol)) {
                if (expression.getClass() == Divide.class) { //можно убрать для нецелочисленного деления
                    sb.append("(");
                    sb.append(expression.toMiniString());
                    sb.append(")");
                } else if (associativity.get(operationSymbol)) {
                    sb.append(expression.toMiniString());
                } else {
                    sb.append("(");
                    sb.append(expression.toMiniString());
                    sb.append(")");
                }
            } else {
                sb.append(expression.toMiniString());
            }
        } else {
            if (expression.getPriority() > priority.get(operationSymbol)) {
                sb.append("(");
                sb.append(expression.toMiniString());
                sb.append(")");
            } else {
                sb.append(expression.toMiniString());
            }
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
    public Calculator<T> getCalc() {
        return calculator;
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
        return leftPart.equals(((AbstractBinary<T>) exp).leftPart)
                && rightPart.equals(((AbstractBinary<T>) exp).rightPart)
                && operationSymbol.equals(((AbstractBinary<T>) exp).operationSymbol);
    }


    public abstract int getPriority();


    public T evaluate(T x, T y, T z) {
        return calculate(leftPart.evaluate(x, y, z), rightPart.evaluate(x, y, z));
    }


    public T calculate(T x, T y) {
        return null;
    }

    @Override
    public int hashCode() {
        return ((7 * leftPart.hashCode() + rightPart.getClass().hashCode()
                + this.getClass().hashCode() + leftPart.getClass().hashCode()) + rightPart.hashCode());
    }
}