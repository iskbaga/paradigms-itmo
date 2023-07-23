package expression;

public class Pow10 extends AbstractUnary implements CommonInterface {

    public Pow10(CommonInterface x) {
        super("pow10", x);
    }

    @Override
    public int calculate(int x) {
        int a = 10;
        int ans = 1;
        while (x > 0) {
            if (x % 2 == 1) {
                ans = ans * a;
            }
            a = a * a;
            x = x / 2;
        }
        return ans;
    }
    @Override
    public String toMiniString() {
        return "pow10 "+expr.toMiniString();
    }


    public String toString() {
        return "pow10("+expr+")";
    }

    @Override
    public double calculate(double x) {
        double a = 2;
        double ans = 1;
        while (x > 0) {
            if (x % 2 == 1) {
                ans = ans * a;
            }
            a = a * a;
            x = x / 2;
        }
        return ans;
    }

}