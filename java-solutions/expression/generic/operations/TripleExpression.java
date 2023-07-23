package expression.generic.operations;


import expression.ToMiniString;

@FunctionalInterface
public interface TripleExpression<T> extends ToMiniString {
    T evaluate(T x, T y, T z);
}
