package expression;

import java.math.BigInteger;

public class Multiply extends AbstractExpression {

    public Multiply(AllMove first, AllMove second) {
        super(first, second, "*");
    }

    @Override
    public BigInteger operation(BigInteger a, BigInteger b) {
        return a.multiply(b);
    }

    @Override
    public int operation(int a, int b) {
        return a * b;
    }

    @Override
    public int priority() {
        return 1;
    }

    @Override
    public boolean check() {
        return true;
    }

    @Override
    public int superPriority() {
        return 1;
    }
}
