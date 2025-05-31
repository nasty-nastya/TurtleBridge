package p02.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Board implements KeyListener {
    private int currentTick;
    private BoardTableModel model;
    public int[][] boardMatrix;
    public Turtle[] turtles;
    public ArrayList<Turtle> activeTurtles;
    public ArrayList<Fish> activeFishes;
    public Player player;
    public Recipient recipient;

    private boolean moveLeft;
    private boolean moveRight;

    private static boolean facingRight;
    private static boolean pickingFlower;
    private static boolean idle;
    private static boolean jump;
    private static boolean flowerGiven;

    private enum PlayerJumpState { NONE, WAITING_TO_FALL }
    private PlayerJumpState jumpState = PlayerJumpState.NONE;
    private int jumpDelayTick = 0;


    public Board() {
        boardMatrix = Constants.BOARD_MATRIX;
        model = new BoardTableModel(boardMatrix);
        turtles = new Turtle[5];
        activeTurtles = new ArrayList<>();
        activeFishes = new ArrayList<>();
        resetGame();
    }

    private void resetGame() {
        for (int row = 0; row < Constants.ROWCOUNT; row++) {
            for (int col = 0; col < Constants.COLCOUNT; col++) {
                boardMatrix[row][col] = BoardEntity.NONE;
                model.updateCell(row, col, BoardEntity.NONE);
            }
        }

        player = new Player(0, 0);
        boardMatrix[player.row][player.col] = BoardEntity.PLAYER;
        model.updateCell(player.row, player.col, BoardEntity.PLAYER);

        recipient = new Recipient(0, Constants.COLCOUNT-1);
        boardMatrix[recipient.row][recipient.col] = BoardEntity.RECIPIENT;
        model.updateCell(recipient.row, recipient.col, BoardEntity.RECIPIENT);

        activeTurtles.clear();
        int turtleRow = 2;
        int i = 0;
        for (int col = 1; col < Constants.COLCOUNT - 1; col += 2) {
            Turtle newTurtle = new Turtle(turtleRow, col);
            turtles[i] = newTurtle;
            activeTurtles.add(newTurtle);
            boardMatrix[turtleRow][col] = BoardEntity.TURTLE;
            model.updateCell(turtleRow, col, BoardEntity.TURTLE);
            i++;
        }

        resetState();
        activeFishes.clear();
        currentTick = 0;
    }

    private void resetState() {
        moveLeft = false;
        moveRight = false;

        facingRight = true;
        jump = false;
        idle = false;
        flowerGiven = false;
        pickingFlower = false;
    }


    public void updateLogic() {
        pickingFlower = false;
        model.updateCell(player.row, player.col, BoardEntity.NONE);
        handleLeftRight();
        handleJumpWait();
        handleIdle();
        handleFlower();

        model.updateCell(player.row, player.col, BoardEntity.PLAYER);

        currentTick++;

        //updating fish and turtles
        updateFishesTurtles();

        //spawn fish with some chance
        if (Math.random() < 0.01) {  //~1%
            int col = 2*(int)(Math.random()*Constants.ROWCOUNT) + 1;
            Fish fish = new Fish(4, col);
            fish.lastMoveTick = currentTick;

            activeFishes.add(fish);
            model.updateCell(fish.row, fish.col, BoardEntity.FISH);
            boardMatrix[fish.row][fish.col] = BoardEntity.FISH;
        }
    }


    private void handleLeftRight() {
        if (moveLeft && player.col > 0) {
            facingRight = false;
            player.moveLeft();
            handleJump();
        }
        if (moveRight && player.col < recipient.col-1) {
            facingRight = true;
            player.moveRight();
            handleJump();
        }
    }

    private void handleIdle() {
        idle = player.col==0 || player.col == Constants.OTHER_BANK_COL;
    }

    private void handleJump() {
        int col = player.col;
        int row = player.row;

        jump = false; //default

        if (row == 0 && col % 2 != 0) {
            player.moveDown();
        }

        else if (row == 1 && col % 2 == 0) {
            player.moveUp();
            jump = true;
            jumpState = PlayerJumpState.WAITING_TO_FALL;
            jumpDelayTick = currentTick + 10; //delay
        }
    }

    private void handleJumpWait() {
        if (jumpState == PlayerJumpState.WAITING_TO_FALL && currentTick >= jumpDelayTick) {
            if (facingRight && player.col < Constants.COLCOUNT - 2) {
                player.moveRight();
                player.moveDown();
            } else if (!facingRight && player.col > 0) {
                player.moveLeft();
                player.moveDown();
            }
            jumpState = PlayerJumpState.NONE;
            jump = false;
        }
    }

    private void handleFlower() {
        if (!flowerGiven && player.col == Constants.OTHER_BANK_COL) {
            flowerGiven = true;
            model.updateCell(recipient.row, recipient.col, BoardEntity.RECIPIENT);
            model.updateCell(0, 0, BoardEntity.FLOWER);
        }
        if (flowerGiven && player.col < Constants.OTHER_BANK_COL) {
            model.updateCell(recipient.row, recipient.col, BoardEntity.RECIPIENT);
        }
        if (player.col==0 && flowerGiven) {
            pickingFlower = true;
            model.updateCell(0, 0, BoardEntity.FLOWER);
            model.updateCell(player.row, player.col, BoardEntity.PLAYER);
            flowerGiven = false;
        }
    }

    public void updateFishesTurtles() {
        ArrayList<Fish> fishesToRemove = new ArrayList<>();

        for (Fish fish : activeFishes) {
            if (!fish.isVisible) continue;

            //move up after 10 tick delay
            if (fish.row == 4 && currentTick - fish.lastMoveTick >= 10) {
                model.updateCell(fish.row, fish.col, BoardEntity.NONE);
                boardMatrix[fish.row][fish.col] = BoardEntity.NONE;

                fish.moveUp(); //up to 3rd row
                fish.lastMoveTick = currentTick;

                model.updateCell(fish.row, fish.col, BoardEntity.FISH);
                boardMatrix[fish.row][fish.col] = BoardEntity.FISH;
            }
        }

        for (Turtle turtle : activeTurtles) {
            // IDLE: check if the fish is on the 3rd row as turtle
            if (turtle.state == TurtleState.IDLE) {
                for (Fish fish : activeFishes) {
                    if (fish.row == 3 && fish.col == turtle.col) {
                        turtle.state = TurtleState.WAITING_TO_DIVE;
                        turtle.waitTick = currentTick + 10;
                        break;
                    }
                }
            }

            // WAITING_TO_DIVE: check 10 ticks to dive
            else if (turtle.state == TurtleState.WAITING_TO_DIVE && currentTick >= turtle.waitTick) {
                // go to 3rd row
                int oldRow = turtle.row;
                turtle.submerge();

                model.updateCell(oldRow, turtle.col, BoardEntity.NONE);
                boardMatrix[oldRow][turtle.col] = BoardEntity.NONE;

                model.updateCell(turtle.row, turtle.col, BoardEntity.TURTLE_SUBMERGED);
                boardMatrix[turtle.row][turtle.col] = BoardEntity.TURTLE_SUBMERGED;

                turtle.state = TurtleState.DIVING;
                turtle.waitTick = currentTick + 30; //turtle eats fish

                //turtle swallows fish
                for (Fish fish : activeFishes) {
                    if (fish.row == turtle.row && fish.col == turtle.col) {
                        fish.getEaten();
                        fishesToRemove.add(fish);
                        break;
                    }
                }
            }
            //DIVING: wait, then go up
            else if (turtle.state == TurtleState.DIVING && currentTick >= turtle.waitTick) {
                int oldRow = turtle.row;
                turtle.emerge();

                model.updateCell(oldRow, turtle.col, BoardEntity.NONE);
                boardMatrix[oldRow][turtle.col] = BoardEntity.NONE;

                model.updateCell(turtle.row, turtle.col, BoardEntity.TURTLE);
                boardMatrix[turtle.row][turtle.col] = BoardEntity.TURTLE;

                turtle.state = TurtleState.IDLE;
            }
        }

        activeFishes.removeAll(fishesToRemove);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (Character.toLowerCase(e.getKeyChar())) {
            case 'a' -> moveLeft = true;
            case 'd' -> moveRight = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (Character.toLowerCase(e.getKeyChar())) {
            case 'a' -> moveLeft = false;
            case 'd' -> moveRight = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}


    public static boolean playerJumping() { return jump; }
    public static boolean playerFacingRight() { return facingRight; }
    public static boolean playerIdling() { return idle; }
    public static boolean flowerGiven() { return flowerGiven; }
    public static boolean playerPickingFlower() { return pickingFlower; }

    public BoardTableModel getModel() { return model; }
}
