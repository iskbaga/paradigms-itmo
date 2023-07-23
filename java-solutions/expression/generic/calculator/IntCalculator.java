package expression.generic.calculator;


import expression.exceptions.OverflowCheck;

public class IntCalculator implements Calculator<Integer> {


    @Override
    public Integer parse(String x) {
        return Integer.valueOf(x);
    }

    @Override
    public Integer parse(int x) {
        return x;
    }

    @Override
    public Integer add(Integer x, Integer y) {
        if (OverflowCheck.add(x, y)) return null;
        return x + y;
    }

    @Override
    public Integer subtract(Integer x, Integer y) {
        if (OverflowCheck.subtract(x, y)) return null;
        return x - y;
    }

    @Override
    public Integer multiply(Integer x, Integer y) {
        if (OverflowCheck.multiply(x, y)) return null;
        return x * y;
    }

    @Override
    public Integer divide(Integer x, Integer y) {
        if (OverflowCheck.divide(x, y)) return null;
        return x / y;
    }

    @Override
    public Integer negate(Integer x) {
        if (OverflowCheck.negate(x)) return null;
        return -x;
    }

    @Override
    public Integer abs(Integer x) {
        if ((OverflowCheck.negate(x) && x < 0)) return null;
        return Math.abs(x);
    }

    @Override
    public Integer square(Integer x) {
        if ((OverflowCheck.multiply(x, x))) return null;
        return x * x;
    }

    @Override
    public Integer mod(Integer x, Integer y) {
        return x % y;
    }
}
