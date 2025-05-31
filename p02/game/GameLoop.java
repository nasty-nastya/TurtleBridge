package p02.game;

public class GameLoop implements Runnable {
    private static final int tickRate = Constants.TICK_RATE;
    private final Board board;
    private final BoardTableModel model;
    private volatile boolean running = true;

    public GameLoop(Board board, BoardTableModel model) {
        this.board = board;
        this.model = model;
    }

    @Override
    public void run() {
        while (running) {
            long startTime = System.currentTimeMillis();
            board.updateLogic();

            long elapsedTime = System.currentTimeMillis() - startTime;
            long sleepTime = Math.max(0, tickRate - elapsedTime);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false;
            }
        }
    }

    public void stop() {
        running = false;
    }
}
