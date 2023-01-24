package core;

import java.util.Scanner;

public class CheckersLogic {
    private Board board;
    private Board.Direction turn;
    private ui.CheckersTextConsole view;
    private int numMoves = 0;

    private int points[] = new int[2];
    Scanner input;

    public CheckersLogic() {
        board = new Board();
        view = new ui.CheckersTextConsole(System.out, board.new BoardView(),
            Board.BOARD_SIZE);
        turn = Board.Direction.UP;
        input = new Scanner(System.in);
    }

    public void start() {
        greetings();

        while(true) {
            updateView();
            String move = getNextMove();
        }
    }

    private boolean canPlayerCapture() {
        return false;
    }

    private String getNextMove() {
        String line = input.nextLine();
        return "";
    }

    private void greetings() {

    }

    private void updateView() {
        view.draw();
    }

    private boolean validateMove(String move) {
        return false;
    }
}