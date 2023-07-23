package expression.generic.operations;

import expression.generic.calculator.Calculator;

public abstract class AbstractOperation<T> implements CommonInterface<T> {
    protected Calculator<T> calculator;

    protected AbstractOperation(Calculator<T> calculator) {
        this.calculator = calculator;
    }

    @Override
    public Calculator<T> getCalc() {
        return calculator;
    }
}
