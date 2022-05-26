package game;

import java.util.Arrays;
import java.util.Map;

public class MnkBoard implements Board {
    private static final Map<Cell, String> CELL_TO_STRING = Map.of(
            Cell.E, ".",
            Cell.X, "X",
            Cell.O, "0"
    );

    private final Cell[][] field;
    private Cell turn;
    private int lastX, lastY, count;
    private int n, m, k;

    public MnkBoard(int n, int m, int k) {
        this.n = n;
        this.m = m;
        this.k = k;

        count = n * m;
        field = new Cell[n][m];
        for (Cell[] row : field) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
    }

    @Override
    public int valRows() {
        return n;
    }

    @Override
    public int valColumns() {
        return m;
    }

    @Override
    public Cell getTurn() {
        return turn;
    }

    @Override
    public GameResult makeMove(Move move) {
        if (!isValid(move)) {
            return GameResult.LOOSE;
        }

        lastX = move.getRow();
        lastY = move.getCol();
        count--;

        field[move.getRow()][move.getCol()] = move.getValue();
        if (checkWin()) {
            return GameResult.WIN;
        }

        if (checkDraw()) {
            return GameResult.DRAW;
        }

        turn = turn == Cell.X ? Cell.O : Cell.X;
        return GameResult.UNKNOWN;
    }

    private boolean checkDraw() {
        return !checkWin() && count == 0;
    }

    private int dist(int dx, int dy) {
        int up = lastX, down = lastX, left = lastY, right = lastY;

        while (0 <= up + dx && up + dx < n && 0 <= left + dy && left + dy < m
                && field[lastX][lastY] == field[up + dx][left + dy]) {
            up += dx;
            left += dy;
        }

        while (0 <= down - dx && down - dx < n && 0 <= right - dy &&  right - dy < m
                && field[lastX][lastY] == field[down - dx][right - dy]) {
            down -= dx;
            right -= dy;
        }

        return Math.max(Math.abs(right - left), Math.abs(up - down) ) + 1;
    }

    private boolean checkWin() {
        return Math.max(Math.max(dist(1, 0), dist(0, 1)),
                Math.max(dist(-1, 1), dist(1, 1)) ) >= k;
    }

    public boolean isValid(final Move move) {
        return 0 <= move.getRow() && move.getRow() < n
                && 0 <= move.getCol() && move.getCol() < m
                && field[move.getRow()][move.getCol()] == Cell.E
                && turn == move.getValue();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(String.format("%2s", " "));
        for (int i = 0; i < m; i++) {
            sb.append(String.format("%2s", " ") + String.format("%2d", i + 1));
        }
        sb.append(System.lineSeparator());
        for (int r = 0; r < n; r++) {
            sb.append(String.format("%2d", r + 1) + String.format("%2s", " "));
            for (Cell cell : field[r]) {
                sb.append(String.format("%2s", CELL_TO_STRING.get(cell)) + String.format("%2s", " "));
            }
            sb.append(System.lineSeparator());
        }
        sb.setLength(sb.length() - System.lineSeparator().length());
        return sb.toString();
    }
}
