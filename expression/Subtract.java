package expression;

import java.math.BigInteger;

public class Subtract extends AbstractExpression {

    public Subtract(AllMove first, AllMove second) {
        super(first, second, "-");
    }

    @Override
    public BigInteger operation(BigInteger a, BigInteger b) {
        return a.subtract(b);
    }

    @Override
    public int operation(int a, int b) {
        return a - b;
    }

    @Override
    public boolean check() {
        return true;
    }

    @Override
    public int priority() {
        return 2;
    }

    @Override
    public int superPriority() {
        return 0;
    }
}
