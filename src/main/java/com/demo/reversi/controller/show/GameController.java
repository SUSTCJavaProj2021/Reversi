package com.demo.reversi.controller.show;

import com.demo.reversi.controller.basic.chess.ChessColor;
import com.demo.reversi.controller.basic.game.Board;
import com.demo.reversi.controller.basic.game.Game;
import com.demo.reversi.controller.basic.game.Step;
import com.demo.reversi.controller.basic.player.Player;
import com.demo.reversi.controller.interfaces.GameControllerLayer;
import com.demo.reversi.controller.interfaces.GameStatus;
import com.demo.reversi.controller.interfaces.GridStatus;
import com.demo.reversi.controller.interfaces.PlayerLayer;
import com.demo.reversi.logger.Log0j;
import com.demo.reversi.save.SaveLoader;
import com.demo.reversi.view.UpdatableGame;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class GameController extends Game implements GameControllerLayer {
    public static final String SAVE_PATH = "games";

    private boolean isReadOnly;
    private UpdatableGame gamePage;
    private GameStatus gameStatus;
    private PlayerController player1, player2;

    public GameController(HumanPlayerController player1, HumanPlayerController player2, Board board, boolean isReadOnly) {
        super(new Player[]{player1.get(), player2.get()}, board);

        this.isReadOnly = isReadOnly;
        gameStatus = GameStatus.UNFINISHED;
        this.player1 = player1;
        this.player2 = player2;
    }

    public GameController(Player[] player, Board board) {
        super(player, board);

        initialize();
    }

    public GameController(Player[] player, ChessColor[] color, boolean[] cheatMode, String name, Board board) {
        super(player, color, cheatMode, name, board);

        initialize();
    }

    public GameController(Scanner scanner, List<Player> list) {
        super(scanner, list);

        initialize();
    }

    private void initialize() {
        isReadOnly = true;
        player1 = player[0].isHuman() ? new HumanPlayerController(player[0]) : new AIPlayerController(player[0]);
        player2 = player[1].isHuman() ? new HumanPlayerController(player[1]) : new AIPlayerController(player[1]);
        judgeGameStatus();
    }

    private void judgeGameStatus() {
        if (isEnded) {
            if (winner == null) {
                gameStatus = GameStatus.TIED;
            } else if (winner.getPid() == player1.pidProperty().getValue()) {
                gameStatus = GameStatus.WIN_PLAYER1;
            } else {
                gameStatus = GameStatus.WIN_PLAYER2;
            }
        } else {
            gameStatus = GameStatus.UNFINISHED;
        }
    }

    @Override
    public void bindToGamePage(UpdatableGame gamePage) {
        this.gamePage = gamePage;
    }

    @Override
    public void unbindGamePage() {
        this.gamePage = null;
    }

    @Override
    public int getRowSize() {
        return board.getRowSize();
    }

    @Override
    public int getColSize() {
        return board.getColumnSize();
    }

    @Override
    public void onGridClick(int row, int col) {
        if (isReadOnly) {
            Log0j.writeInfo(
                    String.format("Invalid Move (%d, %d): The board is read-only", row, col));

            return;
        } else if (!board.isValid(row, col)) {
            Log0j.writeInfo(
                    String.format("Invalid Move (%d, %d): The position is invalid (out of board or banned)", row, col));

            return;
        } else if (board.isCaptured(row, col)) {
            Log0j.writeInfo(
                    String.format("Invalid Move (%d, %d): The position has been captured", row, col));

            return;
        }

        Log0j.writeInfo(
                String.format("%s Clicked Grid (%d, %d)", getCurrentPlayer().nameProperty().getValue(), row, col));

        if (!move(row, col)) {
            pause();

            if (!isMovable()) {
                endGame();
                judgeGameStatus();
            }
        }

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                if (gameStatus != GameStatus.UNFINISHED) {
                    if (gamePage != null) {
                        new Thread(() -> curtainCallUpdate()).start();
                    }
                }

                return null;
            }
        };

        forceSourcedGUIUpdate(row, col, task);
    }

    @Override
    public void forcePause() {
        pause();
    }

    @Override
    public void replayGame() {
        List<Step> list = getStepList();

        for (int i = 0; i < list.size(); i++) {
            performStep(i);
            forceGUIUpdate();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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

    @Override
    public void restartGame() {
        if (isReadOnly) {
            Log0j.writeInfo("Cannot restart because the game is read-only");

            return;
        }

        gameStatus = GameStatus.UNFINISHED;
        backToStart();
        forceGUIUpdate();
    }

    @Override
    public LocalDateTime getGameCreatedTime() {
        return getCreateTime();
    }

    @Override
    public LocalDateTime getGameLastModifiedTime() {
        return getLastModifiedTime();
    }

    @Override
    public GameStatus getGameStatus() {
        return gameStatus;
    }

    @Override
    public void setBlackPlayer(Player player) {
        super.setBlackPlayer(player);
        this.player1.set(player);
    }

    @Override
    public PlayerLayer getPlayer1() {
        return player1;
    }

    @Override
    public void setWhitePlayer(Player player) {
        super.setWhitePlayer(player);
        this.player2.set(player);
    }

    @Override
    public PlayerLayer getPlayer2() {
        return player2;
    }

    @Override
    public void setPlayerCurrent(Player player) {
        super.setPlayerCurrent(player);

        if (curPlayer == 0) {
            this.player1.set(player);
        } else {
            this.player2.set(player);
        }
    }

    @Override
    public PlayerLayer getCurrentPlayer() {
        return curPlayer == 0 ? player1 : player2;
    }

    @Override
    public boolean getCheatMode() {
        //todo: finish this
        return false;
    }

    @Override
    public void setCheatAsPlayer(boolean isPlayer1) {
        if (isPlayer1) {
            super.cheatMode[0] = true;
            Log0j.writeInfo("Cheat mode of player 1 switched: ON");
        } else {
            super.cheatMode[1] = true;
            Log0j.writeInfo("Cheat mode of player 2 switched: ON");
        }
    }

    @Override
    public boolean isUndoAvailable() {
        return !isReadOnly && !getStepList().isEmpty();
    }

    @Override
    public void setCheatMode(boolean isEnabled) {
        super.setCheatMode(isEnabled);
        Log0j.writeInfo("Cheat mode switched: " + (isEnabled ? "ON" : "OFF"));
    }

    @Override
    public boolean undoLastStep() {
        if (isReadOnly) {
            Log0j.writeInfo("Cannot undo last step because the game is read-only");

            return false;
        }

        return undo();
    }

    @Override
    public boolean isPlayer1AI() {
        return !player1.get().isHuman();
    }

    @Override
    public boolean isPlayer2AI() {
        return !player2.get().isHuman();
    }

    @Override
    public GridStatus getGridStatus(int row, int col) {
        if (!board.isValid(row, col)) {
            return GridStatus.BANNED;
        } else if (board.isCaptured(row, col)) {
            return board.getChess()[row][col].getColor() == ChessColor.BLACK ? GridStatus.PLAYER_1 : GridStatus.PLAYER_2;
        } else if (isMovable(row, col)) {
            return GridStatus.AVAILABLE;
        } else {
            return GridStatus.UNOCCUPIED;
        }
    }

    @Override
    public boolean save() {
        return saveTo(new File(SaveLoader.getResource(SAVE_PATH + "/" + gid + ".sav").getAbsolutePath()));
    }

    @Override
    public void setReadOnly(boolean isReadOnly) {
        this.isReadOnly = isReadOnly;
    }
}
