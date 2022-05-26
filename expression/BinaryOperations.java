package expression;

import java.math.BigInteger;

public interface BinaryOperations {
    int operation(int first, int second);
    BigInteger operation(BigInteger first, BigInteger second);
}
