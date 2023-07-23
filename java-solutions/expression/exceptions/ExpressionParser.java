package expression.exceptions;
import expression.Const;
import expression.TripleExpression;
import expression.Variable;
import expression.*;
import expression.parser.Token;
import expression.parser.TokenParser;
public class ExpressionParser implements TripleParser {
    private TokenParser tokenizer;
    private Token token = Token.UNDEFINED;

    private Token prevToken = Token.UNDEFINED;

    int lastPos = 0;

    @Override
    public CommonInterface parse(String expression) throws ParseException {
        tokenizer = new TokenParser(expression);
        getNext();
        CommonInterface exp = parseFourth();
        if (token != Token.END) {
            throw new ParseException("Parse error with token " + token, errorPos());
        }
        return exp;
    }

    private CommonInterface parseFourth() throws ParseException {
        return parseByToken(Token.SET, Token.CLEAR, this::parseThird);
    }

    private CommonInterface parseThird() throws ParseException {
        return parseByToken(Token.ADD, Token.SUB, this::parseSecond);
    }

    private CommonInterface parseSecond() throws ParseException {
        return parseByToken(Token.MUL, Token.DIV, this::parseFirst);
    }

    private CommonInterface parseFirst() throws ParseException {
        CommonInterface exp;
        switch (token) {
            case BRACKET_OPEN -> {
                getNext();
                exp = parseFourth();
                if (token != Token.BRACKET_CLOSE) {
                    throw new ParseException("No close bracket", tokenizer.getPos() + 1);
                }
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
                return new CheckedPow10(parseFirst());
            }
            case LOG10 -> {
                getNext();
                return new CheckedLog10(parseFirst());
            }
            case COUNT -> {
                getNext();
                return new CheckedCount(parseFirst());
            }
            case SUB -> {
                getNext();
                if (token == Token.CONST) {
                    if (tokenizer.getFlag()) {
                        tokenizer.flagReset();
                        return new CheckedNegate(parseFirst());
                    }
                    exp = new Const(Integer.parseInt("-" + tokenizer.getLastToken()));
                    getNext();
                    return exp;
                } else {
                    return new CheckedNegate(parseFirst());
                }
            }
        }
        switch (prevToken) {
            case CONST, BRACKET_CLOSE -> throw new MissingOperationException(lastPos + 1);
            default -> {
                if (prevToken == Token.BRACKET_OPEN && token == Token.BRACKET_CLOSE) {
                    throw new ParseException("Nothing inside the brackets", errorPos());
                }
                throw new MissingOperandException(errorPos());
            }
        }
    }

    private int errorPos() {
        if (tokenizer.getFlag()) {
            return tokenizer.getPos();
        }
        return lastPos + 1;
    }

    private CommonInterface parseByToken(Token token1, Token token2, Pars parser) throws ParseException {
        CommonInterface exp = parser.parse();
        while (token != Token.END) {
            if (token == token1) {
                getNext();
                exp = createExpressionByToken(exp, parser.parse(), token1);
            } else if (token == token2) {
                getNext();
                exp = createExpressionByToken(exp, parser.parse(), token2);
            } else {
                break;
            }
        }
        return exp;
    }

    private CommonInterface createExpressionByToken(CommonInterface left, CommonInterface right, Token token) {
        switch (token) {
            case ADD -> {
                return new CheckedAdd(left, right);
            }
            case SUB -> {
                return new CheckedSubtract(left, right);
            }
            case MUL -> {
                return new CheckedMultiply(left, right);
            }
            case DIV -> {
                return new CheckedDivide(left, right);
            }
            case SET -> {
                return new CheckedSet(left, right);
            }
            case CLEAR -> {
                return new CheckedClear(left, right);
            }
            default -> {
                return null;
            }
        }
    }

    private void getNext() {
        prevToken = token;
        lastPos = tokenizer.getPos();
        token = tokenizer.nextToken();
    }

    public static void main(final String[] args) throws ParseException {
        TripleExpression expr = new ExpressionParser().parse("1 + 2 * 3");
        System.out.println(expr.evaluate(1, 2, 3));
    }


    @FunctionalInterface
    private interface Pars {
        CommonInterface parse() throws ParseException;
    }
}