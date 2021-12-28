package com.demo.reversi.controller.local;

import com.demo.reversi.controller.interfaces.GameControllerLayer;
import com.demo.reversi.controller.interfaces.GameStatus;
import com.demo.reversi.controller.interfaces.GridStatus;
import com.demo.reversi.controller.interfaces.PlayerLayer;
import com.demo.reversi.logger.Log0j;
import com.demo.reversi.save.SaveLoader;
import com.demo.reversi.view.UpdatableGame;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;

import java.io.File;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.Month;

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
    public void onGridClick(int rowIndex, int colIndex) {
        Log0j.writeInfo(
                String.format("%s Clicked Grid (%d, %d)", controller.currentPlayer, rowIndex, colIndex));

        controller.makeMove(rowIndex, colIndex);
        updateCurrentPlayer();

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                GameStatus gameStatus = controller.judge();
                if (gameStatus != GameStatus.UNFINISHED) {
                    if (gamePage != null) {
                        new Thread(() -> curtainCallUpdate()).start();
                    }
                }
                return null;
            }
        };
        forceSourcedGUIUpdate(rowIndex, colIndex, task);
    }

    @Override
    public void forcePause() {

    }

    @Override
    public void replayGame() {

    }

    @Override
    public void restartGame() {
        controller = new CLIGameController(getRowSize(), getColSize());
    }

    @Override
    public LocalDateTime getGameCreatedTime() {
        return LocalDateTime.of(2021, Month.DECEMBER, 24, 12, 02);
    }

    @Override
    public LocalDateTime getGameLastModifiedTime() {
        return LocalDateTime.of(2021, Month.DECEMBER, 24, 12, 02);
    }

    @Override
    public GameStatus getGameStatus() {
        switch(controller.judge()){
            case WIN_PLAYER1 -> {
                return GameStatus.WIN_PLAYER1;
            }
            case WIN_PLAYER2 -> {
                return GameStatus.WIN_PLAYER2;
            }
            case TIED -> {
                return GameStatus.TIED;
            }
            default -> {
                return GameStatus.UNFINISHED;
            }
        }
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
        currentPlayerProperty.setValue(currentPlayer);
    }

    public GridStatus getGridStatus(int rowIndex, int colIndex) {
        return controller.getGridStatus(rowIndex, colIndex);
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
    public boolean isCheatMode() {
        return false;
    }


    @Override
    public void setCheatMode(boolean isEnabled) {
        isCheatMode = isEnabled;
        Log0j.writeInfo("Cheat mode switched: " + (isCheatMode ? "ON" : "OFF"));
    }

    @Override
    public void forceSideSwapping() {
    }

    @Override
    public boolean isUndoAvailable() {
        return false;
    }

    @Override
    public boolean undoLastStep() {
        return false;
    }

    @Override
    public boolean isPlayer1AI() {
        return false;
    }

    @Override
    public boolean isPlayer2AI() {
        return false;
    }

    @Override
    public boolean performAINextStep() {
        return false;
    }


    @Override
    public boolean save() {
        try {
            return saveTo(new File(SaveLoader.class.getResource("DefaultSave.sav").toURI().toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean saveTo(File file) {
        return true;
    }

    @Override
    public void setReadOnly(boolean isReadOnly) {
    }

    ;

    @Override
    public void bindToGamePage(UpdatableGame gamePage) {
        this.gamePage = gamePage;
    }

    @Override
    public void unbindGamePage() {
        this.gamePage = null;
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
    public void forceGUIUpdate(Task<?> task) {
        if (gamePage != null) {
            Task<Void> task1 = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    gamePage.update();
                    return null;
                }
            };
            task1.setOnSucceeded(WorkerStateEvent -> {
                new Thread(task).start();
            });
            new Thread(task1).start();
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

    @Override
    public void forceSourcedGUIUpdate(int row, int col, Task<?> task) {
        if (gamePage != null) {
            gamePage.sourcedUpdate(row, col, task);
        } else {
            Log0j.writeInfo("Cannot update because GUI pointer is null.");
        }
    }

    @Override
    public void curtainCallUpdate() {
        Platform.runLater(gamePage::curtainCallUpdate);
    }

}
