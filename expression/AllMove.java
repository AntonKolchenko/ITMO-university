package expression;

public interface AllMove extends Expression, ToMiniString, TripleExpression, BigIntegerExpression {
    void getStr(StringBuilder str);
    void getMiniStr(StringBuilder str);
    int priority();
    int superPriority();
    boolean check();
}
