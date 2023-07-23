package expression.generic.calculator;

public class LongCalculator implements Calculator<Long> {

    @Override
    public Long parse(String x) {
        return Long.valueOf(x);
    }

    @Override
    public Long parse(int x) {
        return (long) x;
    }

    @Override
    public Long add(Long x, Long y) {
        return x + y;
    }

    @Override
    public Long subtract(Long x, Long y) {
        return x - y;
    }

    @Override
    public Long multiply(Long x, Long y) {
        return x * y;
    }

    @Override
    public Long divide(Long x, Long y) {
        return x / y;
    }

    @Override
    public Long negate(Long x) {
        return -x;
    }

    @Override
    public Long abs(Long x) {
        return Math.abs(x);
    }

    @Override
    public Long square(Long x) {
        return x * x;
    }

    @Override
    public Long mod(Long x, Long y) {
        return x % y;
    }
}
