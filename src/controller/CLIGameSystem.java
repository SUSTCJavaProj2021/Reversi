package controller;

import controller.PlayerConstants;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CLIGameSystem implements GameSystem {

    public final int rowSize = 8, colSize = 8;
    public int board[][];
    public PlayerConstants curPlayer;
    public StringProperty GUICurPlayerProperty;

    public CLIGameSystem() {
        board = new int[rowSize][colSize];
        board[rowSize / 2 - 1][colSize / 2 - 1] = board[rowSize / 2][colSize / 2] = 1;
        board[rowSize / 2 - 1][colSize / 2] = board[rowSize / 2][colSize / 2 - 1] = -1;
        curPlayer = PlayerConstants.WHITE_PLAYER;
        GUICurPlayerProperty = new SimpleStringProperty();
        GUICurPlayerProperty.setValue("Current Player: " + curPlayer.toString());
    }

    @Override
    public BlockStatus getBlockStatus(int rowIndex, int colIndex) {
        if (board[rowIndex][colIndex] == 1) {
            return BlockStatus.WHITE_PLAYER;
        } else if (board[rowIndex][colIndex] == -1) {
            return BlockStatus.BLACK_PLAYER;
        } else {
            return BlockStatus.UNOCCUPIED;
        }
    }

    public void makeMove(int rowIndex, int colIndex) {
        if (board[rowIndex][colIndex] != 0) {
            return;
        }
        board[rowIndex][colIndex] = (curPlayer == PlayerConstants.WHITE_PLAYER) ? 1 : -1;
        makeTurn();
    }

    public void makeTurn() {
        curPlayer = (curPlayer == PlayerConstants.BLACK_PLAYER) ? PlayerConstants.WHITE_PLAYER
                : PlayerConstants.BLACK_PLAYER;
        GUICurPlayerProperty.setValue("Current Player: " + curPlayer.toString());
    }

}
