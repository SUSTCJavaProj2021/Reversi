package com.demo.reversi.controller.local;

import com.demo.reversi.controller.*;
import com.demo.reversi.logger.Log0j;
import com.demo.reversi.view.UpdatableGame;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;

public class SimpleGameController implements GameControllerLayer {
    public boolean isGameModifiable;

    public final PlayerLayer player1;
    public final PlayerLayer player2;
    public PlayerLayer currentPlayer;
    public ObjectProperty<PlayerLayer> currentPlayerProperty;

    public CLIGameController controller;

    public UpdatableGame gamePage;

    public boolean isCheatMode;

    public SimpleGameController(PlayerLayer player1, PlayerLayer player2, int rowSize, int colSize, boolean isGameModifiable) {
        this.isGameModifiable = isGameModifiable;
        this.player1 = player1;
        this.player2 = player2;

        //TEST ONLY
        controller = new CLIGameController(rowSize, colSize);

        isCheatMode = false;
        currentPlayerProperty = new SimpleObjectProperty<>(player1);
        updateCurrentPlayer();
    }

    public SimpleGameController(PlayerLayer player1, PlayerLayer player2, boolean isGameModifiable) {
        this(player1, player2, 8, 8, isGameModifiable);
    }

    @Override
    public int getRowSize() {
        return controller.rowSize;
    }

    @Override
    public int getColSize() {
        return controller.colSize;
    }

    public boolean getModifiability() {
        return this.isGameModifiable;
    }

    @Override
    public boolean onGridClick(int rowIndex, int colIndex) {
        Log0j.writeInfo(
                String.format("%s Clicked Grid (%d, %d)", controller.currentPlayer, rowIndex, colIndex));

        controller.makeMove(rowIndex, colIndex);
        updateCurrentPlayer();
        forceSourcedGUIUpdate(rowIndex, colIndex);

        GameStatus gameStatus = controller.judge();
        if (gameStatus != GameStatus.UNFINISHED) {
            if (gamePage != null) {
                new Thread(()->{
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        Platform.runLater(gamePage::curtainCallUpdate);
                    }
                }).start();
            }
        }
        return true;
    }

    @Override
    public void restartGame() {
        controller = new CLIGameController(8, 8);
    }

    public void updateCurrentPlayer() {
        switch (controller.getCurrentPlayer()) {
            case PLAYER_1:
                currentPlayer = player1;
                break;

            case PLAYER_2:
                currentPlayer = player2;
                break;
        }
        currentPlayerProperty().setValue(currentPlayer);
    }

    public GridStatus getGridStatus(int rowIndex, int colIndex) {
        return controller.getBlockStatus(rowIndex, colIndex);
    }

    @Override
    public PlayerLayer getPlayer1() {
        return player1;
    }

    @Override
    public PlayerLayer getPlayer2() {
        return player2;
    }

    @Override
    public PlayerLayer getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public ObjectProperty<PlayerLayer> currentPlayerProperty() {
        return currentPlayerProperty;
    }


    @Override
    public boolean setCheatMode(boolean isEnabled) {
        isCheatMode = isEnabled;
        Log0j.writeInfo("Cheat mode switched: " + (isCheatMode ? "ON" : "OFF"));
        return true;
    }

    @Override
    public boolean saveGame() {
        return true;
    }

    ;

    @Override
    public boolean save() {
        return saveTo("/save/DefaultSave.save");
    }

    @Override
    public boolean saveTo(String srcPath) {
        return true;
    }

    ;

    @Override
    public boolean bindToGamePage(UpdatableGame gamePage) {
        this.gamePage = gamePage;
        return true;
    }

    @Override
    public void forceGUIUpdate() {

        if (gamePage != null) {
            gamePage.update();
        } else {
            Log0j.writeInfo("Cannot update because GUI pointer is null.");
        }
    }

    @Override
    public void forceSourcedGUIUpdate(int row, int col) {
        if (gamePage != null) {
            gamePage.sourcedUpdate(row, col);
        } else {
            Log0j.writeInfo("Cannot update because GUI pointer is null.");
        }
    }

}
