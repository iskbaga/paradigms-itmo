package expression.generic.calculator;


public class DoubleCalculator implements Calculator<Double> {


    @Override
    public Double parse(String x) {
        return Double.valueOf(x);
    }

    @Override
    public Double parse(int x) {
        return (double) x;
    }

    @Override
    public Double add(Double x, Double y) {
        return x + y;
    }

    @Override
    public Double subtract(Double x, Double y) {
        return x - y;
    }

    @Override
    public Double multiply(Double x, Double y) {
        return x * y;
    }

    @Override
    public Double divide(Double x, Double y) {
        return x / y;
    }

    @Override
    public Double negate(Double x) {
        return -x;
    }

    @Override
    public Double abs(Double x) {
        return Math.abs(x);
    }

    @Override
    public Double square(Double x) {
        return x * x;
    }

    @Override
    public Double mod(Double x, Double y) {
        return x % y;
    }
}
