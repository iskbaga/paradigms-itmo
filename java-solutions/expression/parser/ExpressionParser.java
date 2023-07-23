package expression.parser;

import expression.*;

import java.util.Set;

public class ExpressionParser implements TripleParser {
    private TokenParser tokenizer;
    private Token token = Token.UNDEFINED;

    @Override
    public CommonInterface parse(String expression) {
        tokenizer = new TokenParser(expression);
        getNext();
        return parseFourth();
    }

    private CommonInterface parseFourth() {
        return parseByToken(Set.of(Token.SET, Token.CLEAR), this::parseThird);
    }

    private CommonInterface parseThird() {
        return parseByToken(Set.of(Token.ADD, Token.SUB), this::parseSecond);
    }

    private CommonInterface parseSecond() {
        return parseByToken(Set.of(Token.MUL, Token.MOD, Token.DIV), this::parseFirst);
    }

    private CommonInterface parseFirst() {
        CommonInterface exp = null;
        switch (token) {
            case BRACKET_OPEN -> {
                getNext();
                exp = parseThird();
                getNext();
                return exp;
            }
            case CONST -> {
                exp = new Const(Integer.parseInt(tokenizer.getLastToken()));
                getNext();
                return exp;
            }
            case VAR -> {
                exp = new Variable(tokenizer.getLastToken());
                getNext();
                return exp;
            }
            case POW10 -> {
                getNext();
                return new Pow10(parseFirst());
            }
            case LOG10-> {
                getNext();
                return new Log10(parseFirst());
            }
            case SUB -> {
                getNext();
                if (token == Token.CONST) {
                    if (tokenizer.getFlag()) {
                        tokenizer.flagReset();
                        return new Negate(parseFirst());
                    }
                    exp = new Const( Integer.parseInt("-" + tokenizer.getLastToken()));
                    getNext();
                    return exp;
                } else {
                    return new Negate(parseFirst());
                }
            }
        }
        return exp;
    }

    private CommonInterface parseByToken(Set<Token> tokens, Pars parser) {
        CommonInterface exp = parser.parse();
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


    private CommonInterface createExpressionByToken(CommonInterface left, CommonInterface right, Token token) {
        switch (token) {
            case ADD -> {
                return new Add(left, right);
            }
            case SUB -> {
                return new Subtract(left, right);
            }
            case SET -> {
                return new BinarySet(left, right);
            }
            case CLEAR -> {
                return new BinaryClear(left, right);
            }
            case DIV -> {
                return new Divide(left, right);
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
    private interface Pars {
        CommonInterface parse();
    }
}