package p02.game;

public class Fish extends Entity{
    public boolean isVisible;
    public int lastMoveTick = 0;

    public Fish(int row, int col) {
        super(row, col);
        isVisible = true;
    }

    public void moveUp() {
        if (row > 3) {
            row--;
        }
    }

    public void getEaten() {
        isVisible = false;
    }
}
