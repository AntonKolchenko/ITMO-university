package expression;

import java.math.BigInteger;

public class Variable implements AllMove {

    private String val;

    public Variable(String val) {
        this.val = val;
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public int superPriority() {
        return 0;
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public BigInteger evaluate(BigInteger x) {
        return x;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        switch (val) {
            case "x":
                return x;
            case "y":
                return y;
            case "z":
                return z;
            default:
                throw new UnsupportedOperationException("Some incorrect in evaluate(x, y, z)");
        }
    }

    @Override
    public void getStr(StringBuilder str) {
        str.append(val);
    }

    @Override
    public void getMiniStr(StringBuilder str) {
        str.append(val);
    }

    @Override
    public String toString() {
        return val;
    }

    @Override
    public String toMiniString() {
        return val;
    }

    @Override
    public boolean check() {
        return true;
    }

    @Override
    public int hashCode() {
        return val.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        AllMove temp = (AllMove) obj;
        return this.hashCode() == temp.hashCode();
    }
}
