package p02.pres;

import p02.game.Board;
import p02.game.BoardEntity;
import p02.game.Constants;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class BoardCellRenderer extends DefaultTableCellRenderer {
    private final ImageIcon playerIdleIcon = App.resize(Constants.PLAYER_PATH);
    private final ImageIcon playerIdleFlowerIcon = App.resize(Constants.PLAYER_FLOWER_PATH);
    private final ImageIcon playerIdleFlowerIcon_L = App.resize(Constants.PLAYER_FLOWER_L_PATH);

    private final ImageIcon playerLandIcon = App.resize(Constants.PLAYER_LAND_PATH);
    private final ImageIcon playerLandIcon_L = App.resize(Constants.PLAYER_LAND_L_PATH);
    private final ImageIcon playerLandFlowerIcon = App.resize(Constants.PLAYER_LAND_FLOWER_PATH);
    private final ImageIcon playerLandFlowerIcon_L = App.resize(Constants.PLAYER_LAND_FLOWER_L_PATH);

    private final ImageIcon playerJumpIcon = App.resize(Constants.PLAYER_JUMP_PATH);
    private final ImageIcon playerJumpIcon_L = App.resize(Constants.PLAYER_JUMP_L_PATH);
    private final ImageIcon playerJumpFlowerIcon = App.resize(Constants.PLAYER_JUMP_FLOWER_PATH);
    private final ImageIcon playerJumpFlowerIcon_L = App.resize(Constants.PLAYER_JUMP_FLOWER_L_PATH);

    private final ImageIcon recipientIcon = App.resize(Constants.RECIPIENT_PATH);
    private final ImageIcon recipientFlowerIcon = App.resize(Constants.RECIPIENT_FLOWER_PATH);
    private final ImageIcon recipientFLowerIcon_L = App.resize(Constants.RECIPIENT_FLOWER_L_PATH);

    private final ImageIcon turtleIcon = App.resize(Constants.TURTLE_PATH, Constants.CELL_WIDTH, Constants.CELL_HEIGHT/2);
    private final ImageIcon turtleSubmergedIcon = App.resize(Constants.TURTLE_SUBMERGED_PATH, Constants.CELL_WIDTH, Constants.CELL_HEIGHT);
    private final ImageIcon fishIcon = App.resize(Constants.FISH_PATH, Constants.CELL_WIDTH-15, Constants.CELL_HEIGHT/3);
    private final ImageIcon flowerIcon = App.resize(Constants.FLOWER_PATH, Constants.CELL_WIDTH/3, Constants.CELL_HEIGHT-20);

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, "", false, false, row, column);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setBackground(new Color(0, 0, 0, 0));
        label.setBorder(null);

        int entity = (int) value;

        switch (entity) {
            case BoardEntity.NONE -> label.setIcon(null);
            case BoardEntity.PLAYER -> label.setIcon(getPlayerIcon());
            case BoardEntity.RECIPIENT -> label.setIcon(getRecipientIcon());
            case BoardEntity.TURTLE -> {
                label.setVerticalAlignment(JLabel.TOP);
                label.setIcon(turtleIcon);
            }
            case BoardEntity.FISH -> label.setIcon(fishIcon);
            case BoardEntity.FLOWER -> {
                label.setVerticalAlignment(JLabel.BOTTOM);
                label.setIcon(Board.playerPickingFlower() ? null : flowerIcon);
            }

            case BoardEntity.TURTLE_SUBMERGED -> {
                label.setVerticalAlignment(JLabel.TOP);
                label.setIcon(turtleSubmergedIcon);
            }
        }
        return label;
    }

    private ImageIcon getPlayerIcon() {
        if (Board.flowerGiven()) {
            if (!Board.playerIdling()) {
                return Board.playerFacingRight() ?
                        (Board.playerJumping() ? playerJumpIcon : playerLandIcon) :
                        (Board.playerJumping() ? playerJumpIcon_L : playerLandIcon_L);
            } else {
                return playerIdleIcon;
            }
        }
        else {
            if (!Board.playerIdling()) {
                if (Board.playerFacingRight()) {
                    return Board.playerJumping() ? playerJumpFlowerIcon : playerLandFlowerIcon;
                }
                else {
                    return Board.playerJumping() ? playerJumpFlowerIcon_L : playerLandFlowerIcon_L;
                }
            }
            else {
                return Board.playerFacingRight() ? playerIdleFlowerIcon : playerIdleFlowerIcon_L;
            }
        }
    }

    private ImageIcon getRecipientIcon() {
        if (Board.flowerGiven()) {
            if (!Board.playerIdling()) return recipientFLowerIcon_L;
            else return recipientFlowerIcon;
        }
        else return recipientIcon;
    }
}
