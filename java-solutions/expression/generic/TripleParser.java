package expression.generic;


import expression.generic.calculator.Calculator;
import expression.generic.operations.TripleExpression;

@FunctionalInterface
public interface TripleParser<T> {
    TripleExpression<T> parse(Calculator<T> mode, String expression) throws Exception;
}
