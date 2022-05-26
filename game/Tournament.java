package game;

import java.util.List;

public class Tournament {

    private int[] sum;

    public Tournament(List<Player> list) {
        sum = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (i != j) {
                    final int result = new TwoPlayerGame(
                            new TicTacToeBoard(),
                            new RandomPlayer(),
                            new RandomPlayer()
                            ).play(true);

                    switch (result) {
                        case 1:
                            System.out.println("Player " + Integer.toString(i + 1)
                                    + " won player " + Integer.toString(j + 1));
                            sum[i] += 3;
                            break;
                        case 2:
                            System.out.println("Player " + Integer.toString(i + 1)
                                    + " lost player " + Integer.toString(j + 1));
                            sum[j] += 3;
                            break;
                        case 0:
                            System.out.println("Draw between " + Integer.toString( i + 1) + " "
                                    + Integer.toString( j + 1));
                            sum[i] += 1;
                            sum[j] += 1;
                            break;
                        default:
                            throw new AssertionError("Unknown result " + result);
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();

        string.append("[");
        for (int i = 0; i < sum.length; i++) {
            string.append(sum[i]);
            if (i + 1 != sum.length) {
                string.append(", ");
            }
        }
        string.append("]");

        return string.toString();
    }
}
