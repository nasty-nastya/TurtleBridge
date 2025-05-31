package p02.game;

public class Player extends Entity {
    public Player(int row, int col) {
        super(row, col);
    }
    public void moveLeft() {col--;}
    public void moveRight() {col++;}
    public void moveUp() {row = Math.max(0, row - 1);}
    public void moveDown() {row = Math.min(Constants.ROWCOUNT - 1, row + 1);}
}
