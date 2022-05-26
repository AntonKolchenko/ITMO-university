package expression;

import java.math.BigInteger;

public class Const implements AllMove {

    private BigInteger val;

    public Const(int val) {
        this.val = new BigInteger(String.valueOf(val));
    }

    public Const(BigInteger val) {
        this.val = val;
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
    public int priority() {
        return 0;
    }

    @Override
    public int superPriority() {
        return 0;
    }

    @Override
    public BigInteger evaluate(BigInteger x) {
        return val;
    }

    @Override
    public int evaluate(int x) {
        return val.intValue();
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return val.intValue();
    }

    @Override
    public String toString() {
        return val.toString();
    }

    @Override
    public String toMiniString() {
        return val.toString();
    }

    @Override
    public int hashCode() {
        return val.hashCode();
    }

    @Override
    public boolean check() {
        return true;
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
