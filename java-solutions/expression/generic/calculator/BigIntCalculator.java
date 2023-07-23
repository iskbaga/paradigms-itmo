package expression.generic.calculator;


import java.math.BigInteger;

public class BigIntCalculator implements Calculator<BigInteger> {


    @Override
    public BigInteger parse(String x) {
        return new BigInteger(x);
    }

    @Override
    public BigInteger parse(int x) {
        return BigInteger.valueOf(x);
    }

    @Override
    public BigInteger add(BigInteger x, BigInteger y) {
        return x.add(y);
    }

    @Override
    public BigInteger subtract(BigInteger x, BigInteger y) {
        return x.subtract(y);
    }

    @Override
    public BigInteger multiply(BigInteger x, BigInteger y) {
        return x.multiply(y);
    }

    @Override
    public BigInteger divide(BigInteger x, BigInteger y) {
        return x.divide(y);
    }

    @Override
    public BigInteger negate(BigInteger x) {
        return BigInteger.ZERO.subtract(x);
    }

    @Override
    public BigInteger abs(BigInteger x) {
        return x.abs();
    }

    @Override
    public BigInteger square(BigInteger x) {
        return x.multiply(x);
    }

    @Override
    public BigInteger mod(BigInteger x, BigInteger y) {
        return x.mod(y);
    }
}
