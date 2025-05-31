/*
НОВИНА ДНЯ
мінус 5 = 0
мінус 3 плюс 2 = 0
(с) Барбара.
 */



package p02.game;
import p02.pres.App;

import javax.swing.*;

public class Game {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->{
            App app = new App();
            Thread gameThread = new Thread(new GameLoop(app.board, app.model));
            gameThread.start();
        });
    }
}
