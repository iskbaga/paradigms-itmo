package expression.generic.calculator;


public interface Calculator<T> {
    T parse(String x);
    T parse(int x);
    T add(T x, T y);
    T subtract(T x, T y);
    T multiply(T x, T y);
    T divide(T x, T y);
    T negate(T x);
    T abs(T x);
    T square(T x);
    T mod(T x, T y);

}
