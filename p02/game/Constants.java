package p02.game;

public class Constants {
    public static final int BOARD_WIDTH = 660;
    public static final int BOARD_HEIGHT = 328;
    public static final int TICK_RATE = 80;

    public static final int[][] BOARD_MATRIX = new int[][] {
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };
    public static final int ROWCOUNT = 5;
    public static final int COLCOUNT = 12;
    public static final int OTHER_BANK_COL = 10;

    public static final int CELL_WIDTH = BOARD_WIDTH / 12 +8;
    public static final int CELL_HEIGHT = BOARD_WIDTH / 12;

    public static final int FLOWERGIVEN_POINTS = 3;
    public static final int RETURN_POINTS = 5;
    public static final int MAX_POINTS = 999;

    public static final String PLAYER_PATH = "p02//assets//boyIdle.png";
    public static final String PLAYER_FLOWER_PATH = "p02//assets//boyIdleFlower.png";
    public static final String PLAYER_FLOWER_L_PATH = "p02//assets//boyIdleFlowerLeft.png";

    public static final String PLAYER_LAND_PATH = "p02//assets//boy.png";
    public static final String PLAYER_LAND_L_PATH = "p02//assets//boyLeft.png";
    public static final String PLAYER_LAND_FLOWER_PATH = "p02//assets//boyLandFlower.png";
    public static final String PLAYER_LAND_FLOWER_L_PATH = "p02//assets//boyLandFlowerLeft.png";

    public static final String PLAYER_JUMP_PATH = "p02//assets//boyJump.png";
    public static final String PLAYER_JUMP_L_PATH = "p02//assets//boyJumpLeft.png";
    public static final String PLAYER_JUMP_FLOWER_PATH = "p02//assets//boyFlowerJump.png";
    public static final String PLAYER_JUMP_FLOWER_L_PATH = "p02//assets//boyFlowerJumpLeft.png";

    public static final String RECIPIENT_PATH = "p02//assets//girl.png";
    public static final String RECIPIENT_FLOWER_PATH = "p02//assets//girlFlower.png";
    public static final String RECIPIENT_FLOWER_L_PATH = "p02//assets//girlFlowerLeft.png";

    public static final String BACKGROUND_PATH = "p02//assets//background.png";
    public static final String BOARD_PATH = "p02//assets//board.png";
    public static final String TURTLE_PATH = "p02//assets//turtle.png";
    public static final String TURTLE_SUBMERGED_PATH = "p02//assets//turtleSubmerged.png";
    public static final String FISH_PATH = "p02//assets//fish.png";
    public static final String FLOWER_PATH = "p02//assets//flower.png";
}
