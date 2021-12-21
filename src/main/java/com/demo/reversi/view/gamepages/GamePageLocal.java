package com.demo.reversi.view.gamepages;

import com.demo.reversi.component.MetroButton;
import com.demo.reversi.component.gamemodel.ChessBoard;
import com.demo.reversi.component.panes.ExtendedInfoPane;
import com.demo.reversi.component.panes.InfoPane;
import com.demo.reversi.component.panes.ScorePane;
import com.demo.reversi.controller.GameControllerLayer;
import com.demo.reversi.controller.GameSystemLayer;
import com.demo.reversi.logger.Log0j;
import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.Updatable;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Optional;

public class GamePageLocal implements Updatable {
    public static final double MIN_WIDTH = InfoPane.PREF_WIDTH + ChessBoard.DEFAULT_BOARD_MIN_SIZE;
    public static final double MIN_HEIGHT = ChessBoard.DEFAULT_BOARD_MIN_SIZE;
    public static final double DEFAULT_PREF_WIDTH = 930;
    public static final double DEFAULT_PREF_HEIGHT = 630;

    public final GridPane root;

    public final VBox sidePanel;
    public final GridPane optionsPane;
    public InfoPane player1Info;
    public InfoPane player2Info;
    public ChessBoard chessBoard;

    public final GameSystemLayer gameSystem;
    public GameControllerLayer controller;
    public Theme theme;

    /**
     * @param gameSystem specific game system that allows overall operations.
     * @param index      If <code>-1</code>, then a new game prompt is created. Else, it will load a game from the game system.
     * @param theme      Your preferred theme
     */
    public GamePageLocal(GameSystemLayer gameSystem, int index, Theme theme) {
        this.theme = theme;
        this.gameSystem = gameSystem;
        root = new GridPane();
        root.backgroundProperty().bind(theme.backPanePR());
        root.setMinWidth(MIN_WIDTH);
        root.setMinHeight(MIN_HEIGHT);
        root.setPrefWidth(DEFAULT_PREF_WIDTH);
        root.setPrefHeight(DEFAULT_PREF_HEIGHT);

        sidePanel = new VBox();
        root.add(sidePanel, 1, 0);

        /**
         * Adjust layouts
         */
        {
            ColumnConstraints[] constraints = new ColumnConstraints[2];
            for (int i = 0; i < 2; i++) {
                constraints[i] = new ColumnConstraints();
                root.getColumnConstraints().add(constraints[i]);
            }
            constraints[0].setHgrow(Priority.ALWAYS);
        }
        {
            RowConstraints constraint = new RowConstraints();
            root.getRowConstraints().add(constraint);
            constraint.setVgrow(Priority.ALWAYS);
        }


        /**
         * Adding chessboard and Info Pane
         */
        if (index == -1) {
            Optional<String>[] playerNames = new Optional[2];
            for (int i = 1; i <= 2; i++) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("GameSystem Request");
                dialog.setHeaderText("New Game Request");
                dialog.setContentText("Please enter the name of Player " + i);
                playerNames[i - 1] = dialog.showAndWait();
            }
            if (playerNames[0].isPresent() && playerNames[1].isPresent()) {
                controller = gameSystem.startNewGame(playerNames[0].get(), playerNames[1].get());
            }

        } else {
            controller = gameSystem.loadGame(1, false);
        }
        if (controller != null) {
            chessBoard = new ChessBoard(controller, theme);
            chessBoard.initBoardPlayable();
            BorderPane.setAlignment(chessBoard, Pos.CENTER);
            root.add(chessBoard, 0, 0, 1, 4);
            GridPane.setMargin(chessBoard, new Insets(20, 20, 20, 20));
            GridPane.setHalignment(chessBoard, HPos.CENTER);
            GridPane.setValignment(chessBoard, VPos.CENTER);
            GridPane.setVgrow(chessBoard, Priority.ALWAYS);


            player1Info = new InfoPane(controller.getPlayer1(), theme, theme.player1ChessPaintPR());
            sidePanel.getChildren().add(player1Info);
            player2Info = new InfoPane(controller.getPlayer2(), theme, theme.player2ChessPaintPR());
            sidePanel.getChildren().add(player2Info);
            controller.bindToGamePage(this);
        }


        /**
         * Adding options pane
         */
        optionsPane = new GridPane();

        optionsPane.getColumnConstraints().add(
                new ColumnConstraints(0, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
        sidePanel.getChildren().add(optionsPane);
        VBox.setVgrow(optionsPane, Priority.ALWAYS);


        initOptions();
    }

    public void initOptions() {
        /**
         * Test board judge.
         */
        MetroButton judgeBtn = new MetroButton("Perform board judge!", theme);
        judgeBtn.setOnAction(actionEvent -> {
            chessBoard.curtainCall();
            Platform.runLater(() -> {
                theme.bgmPlayerInterrupt(theme.gameFinishBGMSourcePR().getValue(), 100);
            });
            Stage stage = new Stage();
            stage.setScene(new Scene(new Label("Well played!")));
            stage.show();
            stage.setOnCloseRequest(ActionEvent -> {
                Platform.runLater(() -> {
                    theme.bgmPlayerResume(100);
                });
            });
        });
        optionsPane.add(judgeBtn, 0, 0);


        MetroButton restartBtn = new MetroButton("Restart", theme);
        restartBtn.setOnAction(event -> {
            controller.restartGame();
            update();
        });
        optionsPane.add(restartBtn, 0, 1);


        //todo: switch it to a toggleSwitch
        MetroButton cheatBtn = new MetroButton("Cheat", theme);
        cheatBtn.setOnAction(event -> controller.setCheatMode(true));
        optionsPane.add(cheatBtn, 0, 2);


        //todo: the following buttons shall all be rewritten
        MetroButton pauseBtn = new MetroButton("Pause", theme);
        pauseBtn.setOnAction(event -> Log0j.writeInfo("Game paused on request."));
        optionsPane.add(pauseBtn, 0, 3);

        MetroButton loadAIBtn = new MetroButton("Replace Player to AI", theme);
        loadAIBtn.setOnAction(event -> {
            Log0j.writeInfo("Loading AI player.");
        });
        optionsPane.add(loadAIBtn, 0, 4);


    }

    @Override
    public void update() {
        chessBoard.update();
        updateNoChessBoard();
    }

    public void sourcedUpdate(int row, int col) {
        chessBoard.sourcedUpdate(row, col);
        updateNoChessBoard();
    }

    public void updateNoChessBoard() {
        if (controller.getCurrentPlayer() == controller.getPlayer1()) {
            player2Info.isActivatedProperty().setValue(false);
            player1Info.isActivatedProperty().setValue(true);
        } else {
            player1Info.isActivatedProperty().setValue(false);
            player2Info.isActivatedProperty().setValue(true);
        }
        //todo: add updates
    }


    public GamePageLocal outer() {
        return this;
    }
}
