package expression.parser;

import java.util.HashMap;

public class TokenParser {
    private final String expression;
    private int pos = 0;
    private String lastToken;
    private boolean flag = false;
    private final HashMap<String, Token> commands = new HashMap<>() {{
        put("pow10", Token.POW10);
        put("log10", Token.LOG10);
        put("+", Token.ADD);
        put("-", Token.SUB);
        put("*", Token.MUL);
        put("/", Token.DIV);
        put("set", Token.SET);
        put("clear", Token.CLEAR);
        put("count", Token.COUNT);
        put("(", Token.BRACKET_OPEN);
        put(")", Token.BRACKET_CLOSE);
    }};

    public TokenParser(String expression) {
        this.expression = expression;
    }

    public int getPos() {
        return pos;
    }

    public boolean getFlag() {
        return flag;
    }

    public void flagReset() {
        flag = false;
    }

    private void skip() {
        flag = false;
        while (!eof(0) && (Character.isWhitespace(expression.charAt(pos)))) {
            pos++;
            flag = true;
        }
    }

    private boolean eof(int x) {
        return !(pos + x < expression.length());
    }


    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isLetter(char c) {
        return c >= 'a' && c <= 'z';
    }

    private boolean isVar() {
        return expression.charAt(pos) == 'x' || expression.charAt(pos) == 'y' || expression.charAt(pos) == 'z';
    }

    private void getConst() {
        StringBuilder res = new StringBuilder();
        while (!eof(0) && isDigit(expression.charAt(pos))) {
            res.append(expression.charAt(pos));
            pos++;
        }
        lastToken = res.toString();
    }

    public Token nextToken() {
        skip();
        if (eof(0)) {
            return Token.END;
        }
        if (isDigit(expression.charAt(pos))) {
            getConst();
            return Token.CONST;
        }
        for (String key : commands.keySet()) {
            if (expression.startsWith(key, pos)) {
                if ((key.equals("count") || key.equals("pow10")
                        || key.equals("log10") || key.equals("set") || key.equals("clear"))
                        && (!eof(key.length()) && (isDigit(expression.charAt(pos + key.length()))
                        || isLetter(expression.charAt(pos + key.length()))))) {
                    return Token.UNDEFINED;
                }
                pos += key.length();

                lastToken = expression.substring(pos - key.length(), pos);
                return commands.get(key);
            }
        }
        if (isVar()) {
            pos++;
            lastToken = Character.toString(expression.charAt(pos - 1));
            return Token.VAR;
        }
        return !eof(0) ? Token.UNDEFINED : Token.END;
    }

    public String getLastToken() {
        return lastToken;
    }

}