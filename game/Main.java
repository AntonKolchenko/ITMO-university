package game;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            final int result = new TwoPlayerGame(
                    new HexBoard(),
//                new TicTacToeBoard(),
//                new MnkBoard(3, 4, 2),
                    new RandomPlayer(),
//                new RandomPlayer()
                    new HumanPlayer(new Scanner(System.in))
            ).play(true);
            switch (result) {
                case 1:
                    System.out.println("First player won");
                    break;
                case 2:
                    System.out.println("Second player won");
                    break;
                case 0:
                    System.out.println("Draw");
                    break;
                default:
                    throw new AssertionError("Unknown result " + result);
            }
//        List<Player> have = List.of(new RandomPlayer(), new RandomPlayer(), new RandomPlayer());
//        System.out.println(new Tournament(have));
        } catch (InputMismatchException e) {
            System.out.println("You are bad guy, please read rules of game");
        }
    }
}
