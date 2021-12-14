package controller;

import component.gamemodel.ChessBoard;
import controller.logger.Log0j;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SingleGameController {
    public boolean isGameModifiable;

    public Player whitePlayer;
    public Player blackPlayer;
    public Player currentPlayer;
    public StringProperty curPlayerNameProperty;

    public CLIGameController controller;

    public ChessBoard chessBoard;

    public boolean isCheatMode;

    public SingleGameController(Player whitePlayer, Player blackPlayer, boolean isGameModifiable) {
        this.isGameModifiable = isGameModifiable;
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;

        //TEST ONLY
        controller = new CLIGameController();

        isCheatMode = false;
        curPlayerNameProperty = new SimpleStringProperty(whitePlayer.nameProperty.getValue());
        updateCurrentPlayer();
    }

    public int getRowSize() {
        return controller.rowSize;
    }

    public int getColSize() {
        return controller.colSize;
    }

    public boolean getModifiability() {
        return this.isGameModifiable;
    }

    public void onGridClick(int rowIndex, int colIndex) {
        Log0j.writeLog(
                String.format("%s Clicked Grid (%d, %d)", controller.curPlayer.toString(), rowIndex, colIndex));

        controller.makeMove(rowIndex, colIndex);

        updateCurrentPlayer();
        forceUpdateGUI();
    }

    public void updateCurrentPlayer() {
        switch (controller.getCurPlayer()) {
            case WHITE_PLAYER:
                currentPlayer = whitePlayer;
                break;
            case BLACK_PLAYER:
                currentPlayer = blackPlayer;
                break;
        }
        curPlayerNameProperty.setValue("Current Player: " + currentPlayer.nameProperty.getValue());
    }

    public PlayerConstants getBlockStatus(int rowIndex, int colIndex) {
        return controller.getBlockStatus(rowIndex, colIndex);
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    private void setGameController(CLIGameController controller) {
        this.controller = controller;
    }

    public void bindToChessboard(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    public void setCheatmode(boolean isEnabled) {
        isCheatMode = isEnabled;
        Log0j.writeLog("Cheat mode switched: " + (isCheatMode ? "ON" : "OFF"));
    }

    public void forceUpdateGUI() {

        Log0j.writeLog("Triggering GUI update.");

        chessBoard.updateBoard();
    }

}
