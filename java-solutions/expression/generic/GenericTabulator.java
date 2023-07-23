package expression.generic;
import expression.generic.calculator.*;
import expression.generic.operations.TripleExpression;


import java.util.Map;

public class GenericTabulator implements Tabulator {
    private final Map<String, Calculator<? extends Number>> modes = Map.of(
            "i", new IntCalculator(),
            "d", new DoubleCalculator(),
            "bi", new BigIntCalculator(),
            "u", new UncheckedIntCalculator(),
            "l", new LongCalculator(),
            "s", new ShortCalculator()

    );

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) {
        return table(modes.get(mode), expression, x1, x2, y1, y2, z1, z2);
    }

    public <T extends Number> Object[][][] table(Calculator<T> calculator, String expression, int x1, int x2, int y1, int y2, int z1, int z2) {

        TripleExpression<T> expr = new ExpressionParser<T>().parse(calculator, expression);
        int xSize = x2 - x1 + 1;
        int ySize = y2 - y1 + 1;
        int zSize = z2 - z1 + 1;
        Object[][][] result = new Object[xSize][ySize][zSize];
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                for (int k = 0; k < zSize; k++) {
                    try {
                        result[i][j][k] = expr.evaluate(
                                calculator.parse(x1 + i),
                                calculator.parse(y1 + j),
                                calculator.parse(z1 + k)
                        );
                    } catch (Exception e) {
                        result[i][j][k] = null;
                    }
                }
            }
        }
        return result;
    }

    public static void main(final String[] args) {
        Calculator<Short> calc = new ShortCalculator();
        TripleExpression<Short> expr = new ExpressionParser<Short>().parse(calc, "(x * (y * (-1 + y)))");
        System.out.println(expr.evaluate(
                calc.parse(-6),
                calc.parse(-2),
                calc.parse(-7)));
    }
}
