package expression.exceptions;

import expression.*;

import java.util.HashMap;

public abstract class AbstractCheckedBinary implements CommonInterface {
    private final String operationSymbol;
    private final CommonInterface leftPart;
    private final CommonInterface rightPart;
    private final HashMap<String, Boolean> associativity = new HashMap<>() {{
        put("*", true);
        put("/", false);
        put("+", true);
        put("-", false);
        put("set", false);
        put("clear", false);
    }};
    private final HashMap<String, Integer> priority = new HashMap<>() {{
        put("*", 0);
        put("/", 0);
        put("+", 1);
        put("-", 1);
        put("set", 2);
        put("clear", 2);
    }};

    public AbstractCheckedBinary(String symbol, CommonInterface leftPart, CommonInterface rightPart) {
        operationSymbol = symbol;
        this.leftPart = leftPart;
        this.rightPart = rightPart;
    }

    @Override
    public String toString() {
        return "(" + leftPart.toString() + " " + operationSymbol + " " + rightPart.toString() + ")";
    }

    private void func(StringBuilder sb, CommonInterface expression, boolean right) {
        if (expression instanceof AbstractCheckedUnary || expression.getClass() == Const.class || expression.getClass() == Variable.class) {
            sb.append(expression.toMiniString());
        } else if (right) {
            if (expression.getPriority() > priority.get(operationSymbol)) {
                sb.append("(");
                sb.append(expression.toMiniString());
                sb.append(")");
            } else if (expression.getPriority() == priority.get(operationSymbol)) {
                if (expression.getClass() == CheckedDivide.class) { //можно убрать для нецелочисленного деления
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
    public boolean equals(Object exp) {
        if (exp == null) {
            return false;
        } else if (this == exp) {
            return true;
        } else if (exp.getClass() != this.getClass()) {
            return false;
        }
        return leftPart.equals(((AbstractCheckedBinary) exp).leftPart)
                && rightPart.equals(((AbstractCheckedBinary) exp).rightPart)
                && operationSymbol.equals(((AbstractCheckedBinary) exp).operationSymbol);
    }


    public abstract int getPriority();


    public int evaluate(int x, int y, int z) {
        int res;
        try {
            res = calculate(leftPart.evaluate(x, y, z), rightPart.evaluate(x, y, z));
            return res;
        } catch (CalculateException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    public int calculate(int x, int y) throws CalculateException {
        return 0;
    }


    @Override
    public int hashCode() {
        return ((7 * leftPart.hashCode() + rightPart.getClass().hashCode()
                + this.getClass().hashCode() + leftPart.getClass().hashCode()) + rightPart.hashCode());
    }
}