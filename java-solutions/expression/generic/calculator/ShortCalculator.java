package expression.generic.calculator;

public class ShortCalculator implements Calculator<Short> {
    @Override
    public Short parse(String x) {
        return Short.valueOf(x);
    }

    @Override
    public Short parse(int x) {
        return (short) x;
    }

    @Override
    public Short add(Short x, Short y) {
        return (short) (x + y);
    }

    @Override
    public Short subtract(Short x, Short y) {
        return (short) (x - y);
    }

    @Override
    public Short multiply(Short x, Short y) {
        return (short) (x * y);
    }

    @Override
    public Short divide(Short x, Short y) {
        return (short) (x / y);
    }

    @Override
    public Short negate(Short x) {
        return (short) (-x);
    }

    @Override
    public Short abs(Short x) {
        return (short) (Math.abs(x));
    }

    @Override
    public Short square(Short x) {
        return (short) (x * x);
    }

    @Override
    public Short mod(Short x, Short y) {
        return (short) (x % y);
    }
}
