package expression;

import java.math.BigInteger;

public abstract class AbstractExpression implements AllMove, BinaryOperations {

    private AllMove first, second;
    private String action;

    public AbstractExpression(AllMove first, AllMove second, String action) {
        this.first = first;
        this.second = second;
        this.action = action;
    }

    @Override
    public BigInteger evaluate(BigInteger a) {
        return operation(first.evaluate(a), second.evaluate(a));
    }

    @Override
    public int evaluate(int a) {
        return operation(first.evaluate(a), second.evaluate(a));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return operation(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }

    @Override
    public void getStr(StringBuilder str) {
        str.append("(");
        first.getStr(str);
        str.append(" " + action + " ");
        second.getStr(str);
        str.append(")");
    }

    private boolean needF() {
        return first.priority() > this.priority();
    }

    private boolean needS() {
        if (second.priority() >= this.priority()) {
            if (second.priority() != this.priority() || this.superPriority() != 1 || second.check() != true) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void getMiniStr(StringBuilder str) {
        if (needF()) {
            str.append("(");
        }
        first.getMiniStr(str);
        if (needF()) {
            str.append(")");
        }

        str.append(" " + action + " ");


        if (needS()) {
            str.append("(");
        }
        second.getMiniStr(str);
        if (needS()) {
            str.append(")");
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        getStr(str);
        return str.toString();
    }

    @Override
    public String toMiniString() {
        StringBuilder str = new StringBuilder();
        getMiniStr(str);
        return str.toString();
    }

    @Override
    public int hashCode() {
        int st = 10007;
        return first.hashCode() * st * st + second.hashCode() * st + action.hashCode();
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
