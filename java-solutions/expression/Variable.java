package expression;

public class Variable implements CommonInterface {
    private final String variable;

    public Variable(String x) {
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
                && this.variable.equals(((Variable) object).variable));
    }

    @Override
    public int evaluate(int x, int y, int z) {
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
