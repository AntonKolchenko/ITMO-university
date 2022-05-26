package game;

public interface Position {
    Cell getTurn();
    int valRows();
    int valColumns();
    boolean isValid(Move move);
    String toString();
}
