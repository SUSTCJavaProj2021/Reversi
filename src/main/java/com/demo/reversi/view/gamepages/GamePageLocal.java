package com.demo.reversi.view.gamepages;

import com.demo.reversi.component.MetroButton;
import com.demo.reversi.component.TextLabel;
import com.demo.reversi.component.TitleLabel;
import com.demo.reversi.component.gamemodel.ChessBoard;
import com.demo.reversi.component.panes.InfoPane;
import com.demo.reversi.component.switches.IndicatedToggleSwitch;
import com.demo.reversi.controller.basic.player.Mode;
import com.demo.reversi.controller.interfaces.GameControllerLayer;
import com.demo.reversi.controller.interfaces.GameSystemLayer;
import com.demo.reversi.logger.Log0j;
import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.UpdatableGame;
import com.demo.reversi.view.prompts.PromptLoader;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import java.io.File;

public class GamePageLocal implements UpdatableGame {
    public static final double MIN_WIDTH = InfoPane.PREF_WIDTH + ChessBoard.DEFAULT_BOARD_MIN_SIZE;
    public static final double MIN_HEIGHT = ChessBoard.DEFAULT_BOARD_MIN_SIZE + 40;
    public static final double DEFAULT_PREF_WIDTH = 930;
    public static final double DEFAULT_PREF_HEIGHT = 630;
    public static final double MAX_SIDE_PANEL_WIDTH = InfoPane.PREF_WIDTH * 2;

    public final GridPane root;

    public final VBox sidePanel;
    public final GridPane controlsPane;
    public final VBox configPane;
    public final InfoPane player1Info;
    public final InfoPane player2Info;

    public final IndicatedToggleSwitch cheatToggle;
    public final IndicatedToggleSwitch cheatPlayerToggle;
    public final MetroButton undoButton;
    public final MetroButton saveToButton;
    public final MetroButton recoverPlayer1;
    public final MetroButton recoverPlayer2;
    public final MetroButton setPlayer1AsAI;
    public final MetroButton setPlayer2AsAI;
    public final ComboBox<Mode> player1AIMode;
    public final ComboBox<Mode> player2AIMode;

    private boolean cheatListen;
    private boolean cheatPlayerListen;

    public final ChessBoard chessBoard;
    public final GameSystemLayer gameSystem;
    public GameControllerLayer controller;
    public Theme theme;

    /**
     * This constructor first tries to initialize the overall layout, then it tries to load the controller.
     *
     * @param gameSystem specific game system that allows overall operations.
     * @param controller the required GameController
     * @param theme      Your preferred theme
     */
    public GamePageLocal(GameSystemLayer gameSystem, GameControllerLayer controller, Theme theme) {
        this.theme = theme;
        root = new GridPane();
        root.backgroundProperty().bind(theme.backPanePR());
        root.setMinWidth(MIN_WIDTH);
        root.setMinHeight(MIN_HEIGHT);
        root.setPrefWidth(DEFAULT_PREF_WIDTH);
        root.setPrefHeight(DEFAULT_PREF_HEIGHT);


        //Adding chessboard
        chessBoard = new ChessBoard(theme);
        chessBoard.setShowIndicators(true);
        root.add(chessBoard, 0, 0, 1, 4);
        GridPane.setMargin(chessBoard, new Insets(20));
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
        player1Info = new InfoPane(theme, theme.player1ChessColorPR());
        sidePanel.getChildren().add(player1Info);

        player2Info = new InfoPane(theme, theme.player2ChessColorPR());
        sidePanel.getChildren().add(player2Info);

        sidePanel.getChildren().add(new TitleLabel("Settings", theme));

        cheatToggle = new IndicatedToggleSwitch(theme);
        cheatPlayerToggle = new IndicatedToggleSwitch(theme, "Player 2", "Player 1");

        undoButton = new MetroButton("Undo", theme);
        saveToButton = new MetroButton("Save To...", theme);

        recoverPlayer1 = new MetroButton("Recover Player 1", theme);
        recoverPlayer2 = new MetroButton("Recover Player 2", theme);

        setPlayer1AsAI = new MetroButton("Set Player 1 AI", theme);
        setPlayer2AsAI = new MetroButton("Set Player 2 AI", theme);

        player1AIMode = new ComboBox<>();
        player2AIMode = new ComboBox<>();


        //Adding controls pane
        controlsPane = new GridPane();
        VBox.setVgrow(controlsPane, Priority.ALWAYS);
        sidePanel.getChildren().add(controlsPane);
        initControls();

        //Adding options pane
        configPane = new VBox();
        sidePanel.getChildren().add(configPane);
        VBox.setVgrow(configPane, Priority.ALWAYS);
        initConfigs();


        this.gameSystem = gameSystem;
        this.controller = controller;
        loadController();
        update();
    }

    /**
     * Load the controller.
     */
    public void loadController() {
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
        cheatListen = true;
        cheatPlayerListen = true;
        cheatToggle.switchedOnProperty().addListener(((observable, oldValue, newValue) -> {
            if (cheatListen) {
                controller.setCheatMode(newValue);
            }
        }));

        cheatPlayerToggle.switchedOnProperty().addListener(((observable, oldValue, newValue) -> {
            if (cheatPlayerListen) {
                controller.forceSideSwapping();
                updateWithoutIndicator();
            }
        }));

        controlsPane.add(new HBox(10, new TextLabel("Cheat Mode", theme), cheatToggle), 0, 0, 2, 1);
        controlsPane.add(new HBox(10, new TextLabel("As which player", theme), cheatPlayerToggle), 0, 1, 2, 1);


        undoButton.setOnAction(event -> {
            Log0j.writeInfo("GamePage tried to undo the last operation.");
            controller.undoLastStep();
            update();
        });
        controlsPane.add(undoButton, 0, controlsPane.getRowCount());


        MetroButton saveToButton = new MetroButton("Save To...", theme);
        saveToButton.setOnAction(ActionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save to");
            File file = fileChooser.showSaveDialog(root.getScene().getWindow());
            if (file != null) {
                controller.saveTo(file);
                Log0j.writeInfo("Saved to " + file.getPath());
            } else {
                Log0j.writeError("Player hasn't selected a valid saveTo destination. The saving process had been cancelled.");
            }
        });
        controlsPane.add(saveToButton, 1, controlsPane.getRowCount() - 1);

        {
            //todo: This may need change.
            MetroButton restartBtn = new MetroButton("Restart", theme);
            restartBtn.setOnAction(event -> {
                if (controller != null) {
                    controller.restartGame();
                    chessBoard.initBoardPlayable(controller);
                    update();
                    player1Info.reInit();
                    player2Info.reInit();
                }
            });
            controlsPane.add(restartBtn, 1, controlsPane.getRowCount());
        }


    }


    public void initConfigs() {
        {
            IndicatedToggleSwitch indicatorToggle = new IndicatedToggleSwitch(theme);
            indicatorToggle.switchedOnProperty().setValue(true);
            indicatorToggle.switchedOnProperty().addListener(((observable, oldValue, newValue) -> {
                chessBoard.setShowIndicators(newValue);
                update();
            }));
            configPane.getChildren().add(new HBox(10, new TextLabel("Show Indicators", theme), indicatorToggle));
        }

        {
            //Test board judge.
            MetroButton judgeBtn = new MetroButton("Perform board judge!", theme);
            judgeBtn.setOnAction(actionEvent -> {
                curtainCall();
            });
            configPane.getChildren().add(judgeBtn);
        }

        //AI Controls
        for (Mode d : Mode.values()) {
            player1AIMode.getItems().add(d);
            player2AIMode.getItems().add(d);
        }
        player1AIMode.getSelectionModel().select(0);
        player2AIMode.getSelectionModel().select(0);
        configPane.getChildren().add(new HBox(10, recoverPlayer1, setPlayer1AsAI, player1AIMode));
        configPane.getChildren().add(new HBox(10, recoverPlayer2, setPlayer2AsAI, player2AIMode));

        setPlayer1AsAI.setOnAction(ActionEvent -> {
            controller.setPlayer1AsAIPlayer(player1AIMode.getValue());
        });
        setPlayer2AsAI.setOnAction(ActionEvent -> {
            controller.setPlayer2AsAIPlayer(player2AIMode.getValue());
        });
        recoverPlayer1.setOnAction(ActionEvent -> {
            controller.setRecoverPlayer1AsHuman();
        });
        recoverPlayer2.setOnAction(ActionEvent -> {
            controller.setRecoverPlayer2AsHuman();
        });

        {
            //Test board judge.
            MetroButton judgeBtn = new MetroButton("Perform board judge!", theme);
            judgeBtn.setOnAction(actionEvent -> {
                curtainCall();
            });
            configPane.getChildren().add(judgeBtn);
        }

        {
            //Test board judge.
            MetroButton judgeBtn = new MetroButton("Perform board judge!", theme);
            judgeBtn.setOnAction(actionEvent -> {
                curtainCall();
            });
            configPane.getChildren().add(judgeBtn);
        }

    }

    @Override
    public void update() {
        if (controller == null) {
            return;
        }
        chessBoard.update();
        updateAIPlayer();
        updateElements();
        updateIndicator();
    }

    public void updateWithoutIndicator() {
        if (controller == null) {
            return;
        }
        chessBoard.update();
        updateAIPlayer();
        updateElements();
    }

    @Override
    public void sourcedUpdate(int row, int col) {
        chessBoard.sourcedUpdate(row, col);
        updateAIPlayer();
        updateElements();
        updateIndicator();
    }

    @Override
    public void sourcedUpdate(int row, int col, Task<?> task) {
        chessBoard.sourcedUpdate(row, col, task);
        updateAIPlayer();
        updateElements();
        updateIndicator();
    }

    public void curtainCallUpdate() {
        curtainCall();
    }

    @Override
    public void callInterrupt(Interrupt interrupt, String reason) {
        //todo: Add body
//        Alert alert = PromptLoader.getGameInvalidInterruptAlert(reason, theme);
//        alert.showAndWait();
    }

    private void curtainCall() {
        Log0j.writeCaution("Board judge is triggered. This game page will ignore all future inputs.");
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws InterruptedException {
                Task<Void> showScorePage = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        Platform.runLater(() -> {
                            theme.bgmPlayerInterrupt(theme.gameFinishBGMSourcePR().getValue(), 100);

                            //Show the winning prompt

                            switch (controller.getGameStatus()) {
                                case WIN_PLAYER1 -> {
                                    player1Info.setFinished(InfoPane.Status.WINNER);
                                    player2Info.setFinished(InfoPane.Status.LOSER);
                                }
                                case WIN_PLAYER2 -> {
                                    player2Info.setFinished(InfoPane.Status.WINNER);
                                    player1Info.setFinished(InfoPane.Status.LOSER);
                                }
                                case TIED -> {
                                    player1Info.setFinished(InfoPane.Status.TIED);
                                    player2Info.setFinished(InfoPane.Status.TIED);
                                }
                            }
                            //todo: modify this

                            Alert alert = PromptLoader.getGameFinishAlert(controller, theme);
                            alert.setOnCloseRequest(ActionEvent -> {
                                Platform.runLater(() -> theme.bgmPlayerResumeFromInterrupt(100));
                            });
                            alert.show();

                        });
                        return null;
                    }
                };
                Platform.runLater(theme::bgmPlayerPause);
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
        } else if (controller.getCurrentPlayer() == controller.getPlayer2()) {
            player1Info.isActivatedProperty().setValue(false);
            player2Info.isActivatedProperty().setValue(true);

        }
        recoverPlayer1.setDisable(!controller.isRecoverPlayer1Available());
        recoverPlayer2.setDisable(!controller.isRecoverPlayer2Available());
        undoButton.setDisable(!controller.isUndoAvailable());

        //todo: add updates

    }

    public void updateAIPlayer() {
        if (controller != null) {
            controller.performAINextStep();
        }
    }

    public void updateIndicator() {
        Platform.runLater(() -> {
            cheatListen = false;
            cheatPlayerListen = false;
            cheatToggle.switchedOnProperty().setValue(controller.isCheatMode());
            cheatPlayerToggle.switchedOnProperty().setValue(controller.getCurrentPlayer() != controller.getCurrentPlayer());
            cheatListen = true;
            cheatPlayerListen = true;
        });
    }

    public GamePageLocal outer() {
        return this;
    }

    public void performOnCloseAction() {
        chessBoard.performOnCloseAction();
        controller.save();
        controller.unbindGamePage();
    }

}
