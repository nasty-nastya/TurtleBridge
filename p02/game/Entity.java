package p02.game;

import javax.swing.*;
import java.awt.*;

public abstract class Entity {
    int row;
    int col;

    public Entity(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
