package expression.exceptions;

public class OverflowCheck {
    public static boolean negate(int a) {
        return a == Integer.MIN_VALUE ;
    }

    public static boolean divide(int x, int y) {
        return y == -1 && x == Integer.MIN_VALUE;
    }

    public static boolean multiply(int x, int y) {
        if (x == Integer.MIN_VALUE && !(y == 0 || y == 1)) {
            return true;
        } else if (y == Integer.MIN_VALUE && x != 0 && x != 1) {
            return true;
        } else {
            if (x > 0 && y > 0) {
                return x > Integer.MAX_VALUE / y || y > Integer.MAX_VALUE / x;
            } else if (x < 0) {
                if (y < 0) {
                    return -x > Integer.MAX_VALUE / -y || -y > Integer.MAX_VALUE / -x;
                }
                if (y > 0) {
                    return x < Integer.MIN_VALUE / y;
                }
            } else if (x > 0 && y < 0) {
                return y < Integer.MIN_VALUE / x;
            }
            return false;
        }
    }

    public static boolean add(int x, int y) {
        return x > 0 && y > 0 && y > Integer.MAX_VALUE - x
                || x < 0 && y < 0 && y < Integer.MIN_VALUE - x;
    }

    public static boolean subtract(int x, int y) {
        if (x < 0 && y > 0) {
            return x == Integer.MIN_VALUE || y > Integer.MAX_VALUE + x + 1;
        } else if (x > 0 && y < 0) {
            return y == Integer.MIN_VALUE || x > Integer.MAX_VALUE + y;
        } else return x == 0 && y == Integer.MIN_VALUE;
    }
}
