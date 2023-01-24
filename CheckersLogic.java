package core;

import java.awt.Point;
import java.util.Scanner;

public class CheckersLogic {
    private Board board;
    private Board.Direction turn;
    private ui.CheckersTextConsole view;
    private int numMoves = 0;

    private int points[] = new int[2];
    private Scanner input;

    private int cr, cc, dr, dc;

    private boolean moveLock = false;

    public CheckersLogic() {
        board = new Board();
        view = new ui.CheckersTextConsole(System.out, board.new BoardView(),
            Board.BOARD_SIZE);
        turn = Board.Direction.UP;
        input = new Scanner(System.in);
    }

    private boolean canPlayerCapture() {
        for (int row = 0; row < Board.BOARD_SIZE; ++row) {
            for (int col = 0; col < Board.BOARD_SIZE; ++col) {
                if(!board.isPositionEmpty(row, col) &&
                    board.getDirection(row, col) == turn) {
                    if(board.canCapture(row, col)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public void changePlayerTurn() {
        turn = turn == Board.Direction.UP ?
            Board.Direction.DOWN : Board.Direction.UP;
    }

    public void start() {
        greetings();
        String move;

        while(true) {
            updateView();
            System.out.format("\nPlayer %c -- your turn. Enter your move: ",
                turn == Board.Direction.UP ? 'X' : 'O');
            
            move = input.nextLine();

            if(!validateMove(move)) {
                System.err.println("\nYour move was invalid. " +
                    "Please enter a valid move.\n");
            }
        }
    }

    private Point getCaptee(int cr, int cc, int dr, int dc) {
        if(dr - cr != turn.b_dy()) {
            return null;
        }

        if(dc < cc) {
            if(dc - cc != -2 ||
                board.isPositionEmpty(cc - 1, cr + turn.s_dy()) ||
                board.getDirection(cc - 1, cr + turn.s_dy()) == turn) 
                return null;
            

            return new Point(cc - 1, cr + turn.s_dy());
        } else {
            if(dc - cc != 2 ||
                board.isPositionEmpty(cc + 1, cr + turn.s_dy()) ||
                board.getDirection(cc + 1, cr + turn.s_dy()) == turn)
                return null;

            return new Point(cc + 1, cr + turn.s_dy());
        }
    }

    private void greetings() {

    }

    private void setPosAndDes(String move) {

    }

    private void updateView() {
        view.draw();
    }

    private boolean validateMove(String move) {
        if (move.length() != 5) {
            return false;
        }

        if(move.charAt(0) < 'a' || move.charAt(0) > 'h' || 
           move.charAt(3) < 'a' || move.charAt(3) > 'h' ||
           move.charAt(1) < '0' || move.charAt(1) > '9' || 
           move.charAt(4) < '0' || move.charAt(4) > '9' ||
           move.charAt(2) != '-')  {
            return false;
        }

        if(moveLock) {
            if(move.charAt(1) - 49 != cr || move.charAt(0) - 97 != cc) {
                return false;
            }
        }

        cr = move.charAt(1) - 49;
        cc = move.charAt(0) - 97;
        dr = move.charAt(4) - 49;
        dc = move.charAt(3) - 97;

        if(board.isPositionEmpty(cr, cc) ||
           board.getDirection(cr, cc) != turn ||
           !board.isPositionEmpty(dr, dc)) {
            return false;
        }

        if(!canPlayerCapture()) {
            // check regular move
            if(turn.s_dy() + cr != dr || Math.abs(cc - dc) != 1) {
                return false;
            }

            // make regular move
            
            board.makeMove(cr, cc, dr, dc);
            changePlayerTurn();
        } else {
            Point captee = getCaptee(cr, cc, dr, dc);
            if(captee != null) {
                board.makeMove(cr, cc, dr, dc);
                board.removePawn(captee.x, captee.y);

                if(board.canCapture(dr, dc)) {
                    moveLock = true;
                    cr = dr;
                    cc = dc;
                    System.out.format("\nPlayer %c -- it's still your turn. Continue capturing with pawn at %c%d: \n",
                        turn == Board.Direction.UP ? 'X' : 'O', cc + 97, cr);
                } else {
                    moveLock = false;
                    changePlayerTurn();
                }
            } else {
                System.err.println("\nYou have to capture your opponent's piece.\n");
                return false;
            }
        }

        return true;
    }
}