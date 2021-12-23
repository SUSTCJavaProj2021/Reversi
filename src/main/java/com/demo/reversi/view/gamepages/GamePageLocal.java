package com.demo.reversi.view.gamepages;

import com.demo.reversi.component.MetroButton;
import com.demo.reversi.component.TitleLabel;
import com.demo.reversi.component.gamemodel.ChessBoard;
import com.demo.reversi.component.panes.InfoPane;
import com.demo.reversi.controller.GameControllerLayer;
import com.demo.reversi.controller.GameSystemLayer;
import com.demo.reversi.logger.Log0j;
import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.UpdatableGame;
import com.demo.reversi.view.prompts.PromptLoader;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Optional;

public class GamePageLocal implements UpdatableGame {
    public static final double MIN_WIDTH = InfoPane.PREF_WIDTH + ChessBoard.DEFAULT_BOARD_MIN_SIZE;
    public static final double MIN_HEIGHT = ChessBoard.DEFAULT_BOARD_MIN_SIZE + 40;
    public static final double DEFAULT_PREF_WIDTH = 930;
    public static final double DEFAULT_PREF_HEIGHT = 630;
    public static final double MAX_SIDE_PANEL_WIDTH = InfoPane.PREF_WIDTH * 2;

    public final GridPane root;

    public final VBox sidePanel;
    public final VBox controlsPane;
    public final VBox configPane;
    public InfoPane player1Info;
    public InfoPane player2Info;

    public final ChessBoard chessBoard;
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


        //Adding chessboard
        chessBoard = new ChessBoard(theme);
        root.add(chessBoard, 0, 0, 1, 4);
        GridPane.setMargin(chessBoard, new Insets(20, 20, 20, 20));
        GridPane.setHalignment(chessBoard, HPos.CENTER);
        GridPane.setValignment(chessBoard, VPos.CENTER);
        GridPane.setVgrow(chessBoard, Priority.ALWAYS);
        GridPane.setHgrow(chessBoard, Priority.ALWAYS);

        sidePanel = new VBox(10);
        GridPane.setHgrow(sidePanel, Priority.SOMETIMES);
        root.add(sidePanel, 1, 0);

        //Adjust layouts
        {
            ColumnConstraints[] constraints = new ColumnConstraints[2];
            for (int i = 0; i < 2; i++) {
                constraints[i] = new ColumnConstraints();
                root.getColumnConstraints().add(constraints[i]);
            }
            constraints[1].setHgrow(Priority.SOMETIMES);
        }
        {
            RowConstraints constraint = new RowConstraints();
            root.getRowConstraints().add(constraint);
            constraint.setVgrow(Priority.ALWAYS);
        }


        //Adding Info Pane
        player1Info = new InfoPane(theme, theme.player1ChessPaintPR());
        sidePanel.getChildren().add(player1Info);

        player2Info = new InfoPane(theme, theme.player2ChessPaintPR());
        sidePanel.getChildren().add(player2Info);

        sidePanel.getChildren().add(new TitleLabel("Settings", theme));

        //Adding controls pane
        controlsPane = new VBox(5);
        VBox.setVgrow(controlsPane, Priority.ALWAYS);
        sidePanel.getChildren().add(controlsPane);
        initControls();

        //Adding options pane
        configPane = new VBox();
        sidePanel.getChildren().add(configPane);
        VBox.setVgrow(configPane, Priority.ALWAYS);
        initConfigs();

        loadController(index);
    }

    /**
     * Load the controller.
     *
     * @param index if<code>-1</code>, then a new game will be tried to create
     */
    public void loadController(int index) {
        if (index == -1) {
            Dialog<GameInfo> gameInfoDialog = PromptLoader.getGameInfoDialog(theme);

            Optional<GameInfo> optionalGameInfo = gameInfoDialog.showAndWait();
            optionalGameInfo.ifPresent((GameInfo gameInfo) -> {
                if (gameInfo.rowSize < 0 || gameInfo.colSize < 0) {
                    controller = gameSystem.startNewGame(gameInfo.playerName1, gameInfo.playerName2);
                } else {
                    controller = gameSystem.startNewGame(gameInfo.playerName1, gameInfo.playerName2,
                            gameInfo.rowSize, gameInfo.colSize);
                }
            });
        } else {
            controller = gameSystem.registerGamePlayable(controller);
        }
        if (controller != null) {
            chessBoard.initBoardPlayable(controller);

            player1Info.setPlayer(controller.getPlayer1());
            player2Info.setPlayer(controller.getPlayer2());

            controller.bindToGamePage(this);
            update();
        } else {
            Log0j.writeError("Controller is null because no valid user input is received. Chessboard will not be initialized.");
        }
    }

    public void initControls() {

        //todo: switch it to a toggleSwitch
        MetroButton cheatBtn = new MetroButton("Cheat", theme);
        cheatBtn.setOnAction(event -> controller.setCheatMode(true));
        controlsPane.getChildren().add(cheatBtn);

        //todo: the following buttons shall all be rewritten
        MetroButton undoBtn = new MetroButton("Undo", theme);
        undoBtn.setOnAction(event -> Log0j.writeInfo("GamePage tried to undo the last operation."));
        controlsPane.getChildren().add(undoBtn);

        MetroButton pauseBtn = new MetroButton("Pause", theme);
        pauseBtn.setOnAction(event -> Log0j.writeInfo("Game paused on request."));
        controlsPane.getChildren().add(pauseBtn);

        //todo: This may need change.
        MetroButton restartBtn = new MetroButton("Restart", theme);
        restartBtn.setOnAction(event -> {
            if (controller == null) {
                loadController(-1);
            } else {
                controller.restartGame();
                chessBoard.initBoardPlayable(controller);
            }
            update();
        });
        controlsPane.getChildren().add(restartBtn);

    }


    public void initConfigs() {
        //Test board judge.
        MetroButton judgeBtn = new MetroButton("Perform board judge!", theme);
        judgeBtn.setOnAction(actionEvent -> {
            curtainCall();
        });
        configPane.getChildren().add(judgeBtn);

        MetroButton loadAIBtn = new MetroButton("Replace Player to AI", theme);
        loadAIBtn.setOnAction(event -> {
            Log0j.writeInfo("Loading AI player.");
        });
        configPane.getChildren().add(loadAIBtn);

    }

    @Override
    public void update() {
        if (controller == null) {
            return;
        }
        chessBoard.update();
        updateElements();
    }

    @Override
    public void sourcedUpdate(int row, int col) {
        chessBoard.sourcedUpdate(row, col);
        updateElements();
    }

    @Override
    public void sourcedUpdate(int row, int col, Task<?> task) {
        chessBoard.sourcedUpdate(row, col, task);
        updateElements();
    }

    public void curtainCallUpdate() {
        curtainCall();
    }

    private void curtainCall() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws InterruptedException {
                Task<Void> showScorePage = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        Platform.runLater(() -> {
                            theme.bgmPlayerInterrupt(theme.gameFinishBGMSourcePR().getValue(), 100);

                            //Show the winning prompt


                            //This should be replaced
                            Stage stage = new Stage();
                            stage.setScene(new Scene(new Label("Well played!")));
                            stage.show();
                            stage.setOnCloseRequest(ActionEvent -> {
                                Platform.runLater(() -> {
                                    theme.bgmPlayerResumeFromInterrupt(100);
                                });
                            });

                        });
                        return null;
                    }
                };
                Platform.runLater(theme::bgmPlayerStop);
                chessBoard.curtainCall(showScorePage);
                return null;
            }
        };
        new Thread(task).start();
    }

    /**
     * Update all other elements except the chessboard.
     */
    public void updateElements() {
        if (controller.getCurrentPlayer() == controller.getPlayer1()) {
            player1Info.isActivatedProperty().setValue(true);
            player2Info.isActivatedProperty().setValue(false);
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
