package expression;

public class Log10 extends AbstractUnary implements CommonInterface {

    public Log10(CommonInterface x) {
        super("log10", x);
    }

    @Override
    public int calculate(int x) {
        int res = 0;
        while (x > 1) {
            x /= 10;
            res++;
        }
        return res;
    }
    @Override
    public String toMiniString() {
        return "log10 "+expr.toMiniString();
    }

    public String toString() {
        return "log10("+expr+")";
    }
    @Override
    public double calculate(double x) {
        double res = 0;
        while (x > 1) {
            x /= 10;
            res++;
        }
        return res;
    }

}