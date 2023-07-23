package expression;

import java.util.Objects;

public class Const implements CommonInterface {
    private int anInt;

    public Const(int x) {
        this.anInt = x;
    }

    public int getPriority() {
        return -1;
    }

    public int evaluate(int x) {
        return anInt;
    }


    @Override
    public String toMiniString() {
        return this.toString();
    }

    public String toString() {
        return anInt+"";
    }

    @Override
    public boolean equals(Object exp) {
        return exp != null && this.getClass() == exp.getClass()
                && (Objects.equals(this.anInt, ((Const) exp).anInt));
    }

    @Override
    public int hashCode() {
        return anInt;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return anInt;
    }
}
