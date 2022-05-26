package expression;

import java.math.BigInteger;

public class Divide extends AbstractExpression {

    public Divide(AllMove first, AllMove second) {
        super(first, second, "/");
    }

    @Override
    public BigInteger operation(BigInteger a, BigInteger b) {
        return a.divide(b);
    }

    @Override
    public int operation(int a, int b) {
        return a / b;
    }

    @Override
    public int priority() {
        return 1;
    }

    @Override
    public boolean check() {
        return false;
    }

    @Override
    public int superPriority() {
        return 0;
    }
}
