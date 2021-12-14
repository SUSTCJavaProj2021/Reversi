package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CLIGameController {

    public final int rowSize = 8, colSize = 8;
    public int board[][];
    public PlayerConstants curPlayer;
    public Player whitePlayer;
    public Player blackPlayer;
    public StringProperty GUICurPlayerNameProperty;
    public StringProperty whitePlayerNameProperty;
    public StringProperty blackPlayerNameProperty;

    public CLIGameController() {
        board = new int[rowSize][colSize];
        board[rowSize / 2 - 1][colSize / 2 - 1] = board[rowSize / 2][colSize / 2] = 1;
        board[rowSize / 2 - 1][colSize / 2] = board[rowSize / 2][colSize / 2 - 1] = -1;


        curPlayer = PlayerConstants.WHITE_PLAYER;

    }

    public PlayerConstants getBlockStatus(int rowIndex, int colIndex) {
        if (board[rowIndex][colIndex] == 1) {
            return PlayerConstants.WHITE_PLAYER;
        } else if (board[rowIndex][colIndex] == -1) {
            return PlayerConstants.BLACK_PLAYER;
        } else {
            return PlayerConstants.NO_PLAYER;
        }
    }

    public PlayerConstants getCurPlayer(){
        return curPlayer;
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
    }
}
