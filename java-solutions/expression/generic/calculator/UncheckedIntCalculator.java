package expression.generic.calculator;


public class UncheckedIntCalculator implements Calculator<Integer> {


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
        return x+y;
    }

    @Override
    public Integer subtract(Integer x, Integer y) {
        return x-y;
    }

    @Override
    public Integer multiply(Integer x, Integer y) {
        return x*y;
    }

    @Override
    public Integer divide(Integer x, Integer y) {
        return x/y;
    }

    @Override
    public Integer negate(Integer x) {
        return -x;
    }

    @Override
    public Integer abs(Integer x) {
        return Math.abs(x);
    }

    @Override
    public Integer square(Integer x) {
        return x * x;
    }

    @Override
    public Integer mod(Integer x, Integer y) {
        return x%y;
    }
}
