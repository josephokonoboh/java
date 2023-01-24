package ui;

import core.Board.BoardView;
import java.io.PrintStream;

public class CheckersTextConsole {
    private PrintStream stream;
    private BoardView view;
    private int size;

    public CheckersTextConsole(PrintStream stream, BoardView view, int size) {
        this.stream = stream;
        this.view = view;
        this.size = size;
    }

    public void draw() {
        for(int row = size - 1; row >= 0; --row) {
            stream.format("\n%d |", row + 1);
            for(int col = 0; col < size; ++col) {
                stream.format("%s|", view.getPosStr(row, col));
            }
        }

        stream.print("\n   ");
        for(char c = 'a'; c < 'a' + size; ++c) {
            stream.format(" %c  ", c);
        }
    }
}