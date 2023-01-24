package core;

public class Player {
    private final Board.Direction direction;
    private String userName;

    public Player(Board.Direction direction, String userName) {
        this.direction = direction;
        this.userName = userName;
    }

    public Board.Direction getDirection() {
        return direction;
    }

    public String getUserName() {
        return userName;
    }
}
