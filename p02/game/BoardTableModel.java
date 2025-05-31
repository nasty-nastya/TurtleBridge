package p02.game;

import javax.swing.table.DefaultTableModel;

public class BoardTableModel extends DefaultTableModel {
    public BoardTableModel(int[][] boardData) {
        super(boardDataToObjectArray(boardData), new Object[boardData[0].length]);
    }
    private static Object[][] boardDataToObjectArray(int[][] board) {
        Object[][] data = new Object[board.length][board[0].length];
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                data[row][col] = board[row][col];
            }
        }
        return data;
    }

    public void updateCell(int row, int col, int value) {
        setValueAt(value, row, col);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}