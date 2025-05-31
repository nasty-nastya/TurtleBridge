package p02.pres;

import p02.game.Board;
import p02.game.BoardTableModel;
import p02.game.Constants;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {
    public final Board board;
    public final BoardTableModel model;

    public App() {
        this.board = new Board();
        this.model = board.getModel();

        JTable table = new JTable(model);
        table.setDefaultRenderer(Object.class, new BoardCellRenderer());
        table.setRowHeight(Constants.CELL_HEIGHT);
        table.setShowGrid(false);
        table.setFocusable(false);
        table.setRowSelectionAllowed(false);
        table.setOpaque(false);
        table.setBackground(new Color(0, 0, 0, 0));
        table.setBorder(null);
        table.setBounds(0, 0, Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(Constants.CELL_WIDTH);
        }

        ImageIcon boardIcon = resize(Constants.BOARD_PATH, Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
        JLabel boardLabel = new JLabel(boardIcon);
        boardLabel.setLayout(new OverlayLayout(boardLabel));
        boardLabel.add(table);

        ImageIcon backgroundIcon = new ImageIcon(Constants.BACKGROUND_PATH);
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setLayout(new GridBagLayout());
        backgroundLabel.add(boardLabel);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);
        panel.add(backgroundLabel);

        setTitle("Turtle Bridge");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setContentPane(panel);
        setFocusable(true);
        addKeyListener(board);
        setVisible(true);
        SwingUtilities.invokeLater(this::requestFocusInWindow);
    }

    public static ImageIcon resize(String path) {
        return new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(Constants.CELL_WIDTH, Constants.CELL_HEIGHT, Image.SCALE_DEFAULT));
    }
    public static ImageIcon resize(String path, int width, int height) {
        return new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }
}