package game;

import java.io.IOException;
import java.util.InputMismatchException;

public class TwoPlayerGame {
    private final Board board;
    private final Player player1;
    private final Player player2;

    public TwoPlayerGame(Board board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
    }

    public int play(boolean log) {
        while (true) {
            final int result1 = makeMove(player1, 1, log);
            if (result1 != -1)  {
                return result1;
            }
            final int result2 = makeMove(player2, 2, log);
            if (result2 != -1)  {
                return result2;
            }
        }
    }

    private int makeMove(Player player, int no, boolean log) {
        Move move = null;
        GameResult result;

        while (true) {
            move = player.makeMove(new GetInfo(board));
            if (board.isValid(move)) {
                result = board.makeMove(move);
            } else {
                if (player instanceof HumanPlayer) {
                    System.out.println("Move is incorrect, please get turn on correct position!");
                    continue;
                } else {
                    result = board.makeMove(move);
                }
            }
            break;
        }

        if (log) {
            System.out.println();
            System.out.println("Player: " + no);
            System.out.println(move);
            System.out.println(board);
            System.out.println("Result: " + result);
        }

        switch (result) {
            case WIN:
                return no;
            case LOOSE:
                return 3 - no;
            case DRAW:
                return 0;
            case UNKNOWN:
                return -1;
            default:
                throw new AssertionError("Unknown makeMove result " + result);
        }
    }
}
