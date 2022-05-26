package game;

public interface Board {
    boolean isValid(Move move);
    Cell getTurn();
    GameResult makeMove(Move move);
    String toString();
    int valRows();
    int valColumns();
}
