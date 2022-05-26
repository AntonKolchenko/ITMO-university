package expression;

import java.math.BigInteger;

public class Add extends AbstractExpression {

    public Add(AllMove first, AllMove second) {
        super(first, second, "+");
    }

    @Override
    public BigInteger operation(BigInteger a, BigInteger b) {
        return a.add(b);
    }

    @Override
    public int operation(int a, int b) {
        return a + b;
    }

    @Override
    public int priority() {
        return 2;
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
