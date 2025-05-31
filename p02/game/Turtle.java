package p02.game;

public class Turtle extends Entity {
    public TurtleState state;
    public int waitTick;

    public Turtle(int row, int col) {
        super(row, col);
        this.state = TurtleState.IDLE;
        this.waitTick = 0;
    }

    public void submerge() {
        row=3;
    }

    public void emerge() {
        row=2;
    }
}
