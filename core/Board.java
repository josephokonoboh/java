package core;
    
import java.awt.Point;
import java.util.ArrayList;

public class Board {
    public final static int BOARD_SIZE = 8;
    public static enum Direction {
        UP, DOWN;

        public int b_dy() {
            return this == UP ? 2 : -2;
        }

        public int s_dy() {
            return b_dy() / 2;
        }

        public String str() {
            return this == UP ? " x " : " o ";
        }
    }

    private Pawn[][] pawns;
    private ArrayList<Point> captureList;

    public Board() {
        pawns = new Pawn[BOARD_SIZE][BOARD_SIZE];

        clearBoard();
        fillBoard();

        captureList = new ArrayList<>();
    }

    public void fillCaptureList(int row, int col) {
        captureList.clear();

        if(!isPositionEmpty(row, col)) {
            Pawn p = pawns[row][col];

            int b_dy = p.getDirection().b_dy();
            int s_dy = p.getDirection().s_dy();

            // Check forward left capture
            if(isPositionEmpty(row + b_dy, col - b_dy) &&
               positionHasOpponent(p, row + s_dy, col - s_dy)) {
                captureList.add(new Point(row + s_dy, col - s_dy));
            }

            // Check forward right capture
            if(isPositionEmpty(row + b_dy, col + b_dy) &&
               positionHasOpponent(p, row + s_dy, col + s_dy)) {
                captureList.add(new Point(row + s_dy, col + s_dy));
            }

            if(pawns[row][col].isKing()) {
                // Check backward left capture
                if(isPositionEmpty(row - b_dy, col - b_dy) &&
                positionHasOpponent(p, row - s_dy, col - s_dy)) {
                    captureList.add(new Point(row - s_dy, col - s_dy));
                }

                // Check backward right capture
                if(isPositionEmpty(row - b_dy, col + b_dy) &&
                positionHasOpponent(p, row - s_dy, col + s_dy)) {
                    captureList.add(new Point(row + s_dy, col + s_dy));
                }
            }
        }
    }

    /*public String getPosStr(int row, int col) {
        if(isPositionEmpty(row, col)) {
            return " _ ";
        }

        return pawns[row][col].getDirection().str();
    }*/

    public boolean isPositionEmpty(int row, int col) {
        if(isPositionValid(row, col)) {
            return pawns[row][col] == null;
        }

        return false;
    }

    public boolean isPositionValid(int row, int col) {
        return 0 <= row && row < BOARD_SIZE &&
               0 <= col && col < BOARD_SIZE;
    }

    public boolean positionHasOpponent(Pawn p, int row, int col) {
        return !isPositionEmpty(row, col) &&
            pawns[row][col].getDirection() != p.getDirection();
    }

    public void removePawn(int row, int col) {
        if(isPositionValid(row, col)) {
            pawns[row][col] = null;
        }
    }

    public class BoardView {
        public String getPosStr(int row, int col) {
            if(isPositionEmpty(row, col)) {
                return " _ ";
            }

            return pawns[row][col].getDirection().str();
        }
    }

    private void addPawnToBoard(Pawn p, int row, int col) {
        if(isPositionEmpty(row, col)) {
            pawns[row][col] = p;
        }
    }

    private void clearBoard() {
        for(int row = 0; row < BOARD_SIZE; ++row) {
            for(int col = 0; col < BOARD_SIZE; ++col) {
                pawns[row][col] = null;
            }
        }
    }

    private void fillBoard() {
        addPawnToBoard(new Pawn(Board.Direction.UP), 0, 0);
        addPawnToBoard(new Pawn(Board.Direction.UP), 0, 2);
        addPawnToBoard(new Pawn(Board.Direction.UP), 0, 4);
        addPawnToBoard(new Pawn(Board.Direction.UP), 0, 6);

        addPawnToBoard(new Pawn(Board.Direction.UP), 1, 1);
        addPawnToBoard(new Pawn(Board.Direction.UP), 1, 3);
        addPawnToBoard(new Pawn(Board.Direction.UP), 1, 5);
        addPawnToBoard(new Pawn(Board.Direction.UP), 1, 7);

        addPawnToBoard(new Pawn(Board.Direction.UP), 2, 0);
        addPawnToBoard(new Pawn(Board.Direction.UP), 2, 2);
        addPawnToBoard(new Pawn(Board.Direction.UP), 2, 4);
        addPawnToBoard(new Pawn(Board.Direction.UP), 2, 6);

        addPawnToBoard(new Pawn(Board.Direction.DOWN), 7, 1);
        addPawnToBoard(new Pawn(Board.Direction.DOWN), 7, 3);
        addPawnToBoard(new Pawn(Board.Direction.DOWN), 7, 5);
        addPawnToBoard(new Pawn(Board.Direction.DOWN), 7, 7);

        addPawnToBoard(new Pawn(Board.Direction.DOWN), 6, 0);
        addPawnToBoard(new Pawn(Board.Direction.DOWN), 6, 2);
        addPawnToBoard(new Pawn(Board.Direction.DOWN), 6, 4);
        addPawnToBoard(new Pawn(Board.Direction.DOWN), 6, 6);

        addPawnToBoard(new Pawn(Board.Direction.DOWN), 5, 1);
        addPawnToBoard(new Pawn(Board.Direction.DOWN), 5, 3);
        addPawnToBoard(new Pawn(Board.Direction.DOWN), 5, 5);
        addPawnToBoard(new Pawn(Board.Direction.DOWN), 5, 7);
    }
}
