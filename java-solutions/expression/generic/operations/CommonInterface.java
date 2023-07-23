package expression.generic.operations;

import expression.ToMiniString;
import expression.generic.calculator.Calculator;

public interface CommonInterface<T> extends TripleExpression<T>, ToMiniString {

    Calculator<T> getCalc();

    int getPriority();
}
