package expression.generic;
import expression.generic.calculator.Calculator;
import expression.generic.operations.*;
import expression.parser.Token;

import java.util.Set;

public class ExpressionParser<T extends Number> implements TripleParser<T> {
    private TokenParser tokenizer;
    private Token token = Token.UNDEFINED;
    public Calculator<T> calculator;

    @Override
    public CommonInterface<T> parse(Calculator<T> calc, String expression) {
        calculator = calc;
        tokenizer = new TokenParser(expression);
        getNext();
        return parseThird();
    }

    private CommonInterface<T> parseThird() {
        return parseByToken(Set.of(Token.ADD, Token.SUB), this::parseSecond);
    }

    private CommonInterface<T> parseSecond() {
        return parseByToken(Set.of(Token.MUL, Token.MOD, Token.DIV), this::parseFirst);
    }

    private CommonInterface<T> parseFirst() {
        CommonInterface<T> exp = null;
        switch (token) {
            case BRACKET_OPEN -> {
                getNext();
                exp = parseThird();
                getNext();
                return exp;
            }
            case CONST -> {
                exp = new Const<>(calculator, tokenizer.getLastToken());
                getNext();
                return exp;
            }
            case VAR -> {
                exp = new Variable<>(calculator, tokenizer.getLastToken());
                getNext();
                return exp;
            }
            case ABS -> {
                getNext();
                return new ABS<>(parseFirst());
            }
            case SQUARE -> {
                getNext();
                return new Square<>(parseFirst());
            }
            case SUB -> {
                getNext();
                if (token == Token.CONST) {
                    if (tokenizer.getFlag()) {
                        tokenizer.flagReset();
                        return new Negate<>(parseFirst());
                    }
                    exp = new Const<>(calculator, "-" + tokenizer.getLastToken());
                    getNext();
                    return exp;
                } else {
                    return new Negate<>(parseFirst());
                }
            }
        }
        return exp;
    }

    private CommonInterface<T> parseByToken(Set<Token> tokens, Pars<T> parser) {
        CommonInterface<T> exp = parser.parse();
        for (Token tempToken : tokens) {
            if (tempToken == token) {
                getNext();
                exp = createExpressionByToken(exp, parser.parse(), tempToken);
            } else if (tempToken == Token.END) {
                break;
            }
        }
        return exp;
    }


    private CommonInterface<T> createExpressionByToken(CommonInterface<T> left, CommonInterface<T> right, Token token) {
        switch (token) {
            case ADD -> {
                return new Add<>(left, right);
            }
            case MOD -> {
                return new Mod<>(left, right);
            }
            case SUB -> {
                return new Subtract<T>(left, right);
            }
            case MUL -> {
                return new Multiply<T>(left, right);
            }
            case DIV -> {
                return new Divide<T>(left, right);
            }
            default -> {
                return null;
            }
        }
    }

    private void getNext() {
        token = tokenizer.nextToken();
    }

    @FunctionalInterface
    private interface Pars<T> {
        CommonInterface<T> parse();
    }
}