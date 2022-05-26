package game;

public class GetInfo implements Position {
    private Board board;

    public GetInfo(Board board) {
        this.board = board;
    }

    @Override
    public String toString() {
        return board.toString();
    }

    @Override
    public int valRows() {
        return board.valRows();
    }

    @Override
    public int valColumns() {
        return board.valColumns();
    }

    @Override
    public Cell getTurn() {
        return board.getTurn();
    }

    public boolean isValid(Move move) {
        return board.isValid(move);
    }
}
