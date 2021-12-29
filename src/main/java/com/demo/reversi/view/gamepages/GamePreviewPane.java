package com.demo.reversi.view.gamepages;

import com.demo.reversi.MainApp;
import com.demo.reversi.component.TitleLabel;
import com.demo.reversi.component.gamemodel.ChessBoard;
import com.demo.reversi.component.panes.InfoPane;
import com.demo.reversi.controller.interfaces.GameControllerLayer;
import com.demo.reversi.controller.interfaces.GameSystemLayer;
import com.demo.reversi.controller.show.GameController;
import com.demo.reversi.logger.Log0j;
import com.demo.reversi.save.SaveLoader;
import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.prompts.PromptLoader;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class GamePreviewPane extends StackPane {
    //todo: set the following animations:
    /*
     * Animation list:
     * hoverAnimation
     * clickAnimation
     */

    /*
     * Basic layout structure is illustrated as follows
     *
     * ----------------------
     * -     ChessBoard     -
     * ----------------------
     * -Player 1 vs Player 2-
     * ----------------------
     * -  Last played time  -
     * ----------------------
     */

    public static final double MIN_HEIGHT = 300;
    public static final double MIN_WIDTH = 300;
    public static final double CHESSBOARD_SIZE = 270;
    public static final double PREF_MARGIN = 15;
    public static final double OPACITY_DEFAULT = 0.8;
    public static final double OPACITY_SELECTED = 1.0;
    public static final double OPACITY_PRESSED = 0.7;
    public static final double TRANS_TIME_MILLIS = 150;


    public final Theme theme;
    public final GridPane container;
    public final StackPane viewCover; //Used to display hovering animations
    public final GridPane infoSumPane;
    public final InfoPane player1Info;
    public final InfoPane player2Info;
    public final Label gameStatusLabel;
    public final Label lastModifiedTimeLabel;
    public final Label createdTimeLabel;

    public final GameSystemLayer gameSystem;
    public final ChessBoard chessBoard;

    public GameControllerLayer controller;

    public enum PreviewType {
        NEW_GAME, LOAD_GAME_FROM_FILE, LOAD_GAME, TUTORIAL;
    }

    /**
     * This constructor is used for "Preview Game" selection
     * Notice that player should be able to directly load game from this by clicking the preview pane.
     *
     * @param gameSystem GameSystem
     * @param controller GameController
     * @param theme      Theme
     */
    public GamePreviewPane(GameSystemLayer gameSystem, GameControllerLayer controller, Theme theme) {
        setCache(true);
        this.gameSystem = gameSystem;
        this.controller = controller;
        this.theme = theme;

        container = new GridPane();

        viewCover = new StackPane();

        infoSumPane = new GridPane();

        chessBoard = new ChessBoard(theme, CHESSBOARD_SIZE);
        chessBoard.initBoardDemo(controller);

        player1Info = new InfoPane(theme, theme.player1ChessColorPR());
        player2Info = new InfoPane(theme, theme.player2ChessColorPR());
        gameStatusLabel = new Label();
        createdTimeLabel = new Label();
        lastModifiedTimeLabel = new Label();

        initLayout(PreviewType.LOAD_GAME);
        initAnimation();
        initLoadGameAction();

        loadInfo();
    }

    /**
     * This constructor is used for "New Game" creation and "Load Game from File" creation
     *
     * @param gameSystem GameSystem
     * @param theme      Theme
     */
    public GamePreviewPane(GameSystemLayer gameSystem, Theme theme, PreviewType previewType) {
        setCache(true);
        this.gameSystem = gameSystem;
        this.theme = theme;

        container = new GridPane();

        viewCover = new StackPane();

        infoSumPane = new GridPane();

        chessBoard = new ChessBoard(theme, CHESSBOARD_SIZE);

        player1Info = new InfoPane(theme, theme.player1ChessColorPR());
        player2Info = new InfoPane(theme, theme.player2ChessColorPR());
        gameStatusLabel = new Label();
        createdTimeLabel = new Label();
        lastModifiedTimeLabel = new Label();

        loadInfo();

        if (previewType == PreviewType.NEW_GAME) {
            initLayout(PreviewType.NEW_GAME);
            initAnimation();
            initNewGameAction();
        } else if (previewType == PreviewType.LOAD_GAME_FROM_FILE) {
            initLayout(PreviewType.LOAD_GAME_FROM_FILE);
            initAnimation();
            initLoadGameFromFileAction();
        } else if (previewType == PreviewType.TUTORIAL) {
            initLayout(PreviewType.TUTORIAL);
            initAnimation();
            initTutorialAction();
        }

    }

    /**
     * Initialize InfoPanes, Labels
     */
    public void loadInfo() {
        if (controller != null) {
            //todo: change this
            String s = "Undefined";

            player1Info.setPlayer(controller.getPlayer1());
            player2Info.setPlayer(controller.getPlayer2());
            switch (controller.getGameStatus()) {
                case WIN_PLAYER1 -> {
                    player1Info.setFinished(InfoPane.Status.WINNER);
                    player2Info.setFinished(InfoPane.Status.LOSER);
                    s = "Winner\n" + controller.getPlayer1().nameProperty().getValue();
                }
                case WIN_PLAYER2 -> {
                    player2Info.setFinished(InfoPane.Status.WINNER);
                    player1Info.setFinished(InfoPane.Status.LOSER);
                    s = "Winner\n" + controller.getPlayer2().nameProperty().getValue();
                }
                case TIED -> {
                    player1Info.setFinished(InfoPane.Status.TIED);
                    player2Info.setFinished(InfoPane.Status.TIED);
                    s = "Tied";
                }
                case UNFINISHED -> {
                    s = "Unfinished";
                }
            }

            gameStatusLabel.setText(s);
            lastModifiedTimeLabel.setText("Last modified on:\t" +
                    controller.getGameLastModifiedTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm")));
            createdTimeLabel.setText("Created on:\t\t" +
                    controller.getGameCreatedTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm")));
        } else {
            gameStatusLabel.setText("Null");
            lastModifiedTimeLabel.setText("Last modified on:\t2000-0-0, 00:00");
            createdTimeLabel.setText("Created on:\t\t2000-0-0, 00:00");
        }
    }

    private void initLayout(PreviewType type) {
        //todo: finish layout settings
        container.setPrefWidth(MIN_WIDTH);
        container.setMinHeight(MIN_HEIGHT);
        container.backgroundProperty().bind(theme.frontPanePR());

        container.add(chessBoard, 0, 0);
        GridPane.setConstraints(chessBoard, 0, 0, 1, 1,
                HPos.CENTER, VPos.CENTER, Priority.SOMETIMES, Priority.ALWAYS, new Insets(PREF_MARGIN));


        container.add(infoSumPane, 0, 1);
        infoSumPane.add(player1Info, 0, 0);
        infoSumPane.add(player2Info, 0, 1);
        infoSumPane.add(gameStatusLabel, 1, 0, 1, 2);
        ColumnConstraints constraints = new ColumnConstraints();
        constraints.setPercentWidth(70);
        infoSumPane.getColumnConstraints().add(constraints);
        GridPane.setMargin(infoSumPane, new Insets(PREF_MARGIN));

        GridPane.setHalignment(gameStatusLabel, HPos.CENTER);
        GridPane.setValignment(gameStatusLabel, VPos.CENTER);
        GridPane.setVgrow(gameStatusLabel, Priority.SOMETIMES);
        GridPane.setHgrow(gameStatusLabel, Priority.SOMETIMES);
        gameStatusLabel.textFillProperty().bind(theme.textFontPaintPR());
        gameStatusLabel.fontProperty().bind(theme.textFontFamilyPR());
        gameStatusLabel.setWrapText(true);


        lastModifiedTimeLabel.textFillProperty().bind(theme.textFontPaintPR());
        createdTimeLabel.textFillProperty().bind(theme.textFontPaintPR());
        lastModifiedTimeLabel.fontProperty().bind(theme.textFontFamilyPR());
        createdTimeLabel.fontProperty().bind(theme.textFontFamilyPR());
        container.add(lastModifiedTimeLabel, 0, 2);
        container.add(createdTimeLabel, 0, 3);
        GridPane.setMargin(lastModifiedTimeLabel, new Insets(0, PREF_MARGIN, 0, PREF_MARGIN));
        GridPane.setMargin(createdTimeLabel, new Insets(0, PREF_MARGIN, 0, PREF_MARGIN));

        TitleLabel indicator;
        switch (type) {
            case NEW_GAME -> {
                indicator = new TitleLabel("New Game", theme);
                gameStatusLabel.setText("New Game");
            }
            case LOAD_GAME -> indicator = new TitleLabel("Load", theme);
            case LOAD_GAME_FROM_FILE -> {
                indicator = new TitleLabel("Load from File", theme);
                gameStatusLabel.setText("Load from File");
            }
            case TUTORIAL -> {
                indicator = new TitleLabel("Start Tutorial", theme);
                gameStatusLabel.setText("Tutorial");
            }
            default -> indicator = new TitleLabel("Demo", theme);
        }
        indicator.setWrapText(true);
        viewCover.getChildren().add(indicator);

        getChildren().addAll(container, viewCover);
    }

    private void initAnimation() {
        viewCover.setVisible(false);

        BoxBlur blur = new BoxBlur();
        blur.setIterations(0);
        container.setEffect(blur);

        setOnMouseEntered(event -> {
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(TRANS_TIME_MILLIS),
                    new KeyValue(blur.iterationsProperty(), 6)));

            FadeTransition ft = new FadeTransition(Duration.millis(TRANS_TIME_MILLIS), viewCover);
            ft.setFromValue(OPACITY_DEFAULT);
            ft.setToValue(OPACITY_SELECTED);
            ft.setCycleCount(1);

            Platform.runLater(() -> {
                viewCover.setVisible(true);
                ft.play();
                timeline.play();
            });
        });

        setOnMouseExited(event -> {
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(TRANS_TIME_MILLIS),
                    new KeyValue(blur.iterationsProperty(), 0)));

            FadeTransition ft = new FadeTransition(Duration.millis(TRANS_TIME_MILLIS), viewCover);
            ft.setFromValue(OPACITY_SELECTED);
            ft.setToValue(OPACITY_DEFAULT);
            ft.setCycleCount(1);

            ft.setOnFinished(ActionEvent -> {
                viewCover.setVisible(false);
            });

            Platform.runLater(() -> {
                ft.play();
                timeline.play();
            });
        });
    }

    private void initLoadGameFromFileAction() {
        setOnMouseClicked(MouseEvent -> {
            //todo: finish this.
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Save");
            fileChooser.setInitialDirectory(new File(SaveLoader.getResource(GameController.SAVE_PATH + "/").getAbsolutePath()));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Save File", "*.sav"));
            File selectedFile = fileChooser.showOpenDialog(getScene().getWindow());
            if (selectedFile != null) {
                controller = gameSystem.loadGame(selectedFile);
                if (controller != null) {
                    GamePageLocal gamePageLocal = new GamePageLocal(gameSystem, gameSystem.registerGamePlayable(controller), theme);
                    initGameToStage(gamePageLocal, PreviewType.LOAD_GAME_FROM_FILE);
                    controller = null;
                }
            } else {
                Log0j.writeError("No file is selected. Loading failed.");
            }
        });
    }


    private void initLoadGameAction() {
        setOnMouseClicked(MouseEvent -> {
            GamePageLocal gameLocalPage = new GamePageLocal(gameSystem, gameSystem.registerGamePlayable(controller), theme);
            initGameToStage(gameLocalPage, PreviewType.LOAD_GAME);
        });
    }

    private void initNewGameAction() {
        setOnMouseClicked(MouseEvent -> {
            Dialog<GameInfo> gameInfoDialog = PromptLoader.getGameInfoDialog(theme);

            Optional<GameInfo> optionalGameInfo = gameInfoDialog.showAndWait();

            optionalGameInfo.ifPresent((GameInfo gameInfo) -> {
                //todo: change AI player settings
                if (!gameInfo.isEditorApplied) {
                    controller = gameSystem.startNewGame(gameInfo.playerName1, gameInfo.isPlayer1AI, gameInfo.player1Mode,
                            gameInfo.playerName2, gameInfo.isPlayer2AI, gameInfo.player2Mode, gameInfo.rowSize, gameInfo.colSize);
                } else {
                    controller = gameSystem.startNewGame(gameInfo.playerName1, gameInfo.isPlayer1AI, gameInfo.player1Mode,
                            gameInfo.playerName2, gameInfo.isPlayer2AI, gameInfo.player2Mode, true);
                }
            });

            if (controller != null) {
                GamePageLocal gameLocalPage = new GamePageLocal(gameSystem, gameSystem.registerGamePlayable(controller), theme);
                initGameToStage(gameLocalPage, PreviewType.NEW_GAME);

                //Resetting the GameController
                controller = null;
            } else {
                Log0j.writeInfo("Game loading failed for unknown reason. The scenario was considered not going to happen.");
            }
        });

    }

    private void initTutorialAction() {
        setOnMouseClicked(MouseEvent -> {
            controller = gameSystem.startNewGame("Test Player 1", "Test Player 2");
            TutorialPage tutorialPage = new TutorialPage(gameSystem, controller, theme);
            Stage gameStage = new Stage();
            gameStage.setScene(new Scene(tutorialPage.root));
            gameStage.setTitle("Tutorial Game");
            gameStage.getIcons().add(Theme.getAppIcon());

            gameStage.setMinWidth(GamePageLocal.MIN_WIDTH);
            gameStage.setMinHeight(GamePageLocal.MIN_HEIGHT);

            gameStage.show();
            Platform.runLater(theme::registerGameBGM);
            Log0j.writeInfo("Tutorial Game initialized.");
            gameStage.setOnHidden(ActionEvent -> {
                tutorialPage.performOnCloseAction();
                Platform.runLater(theme::unregisterGameBGM);
            });
        });

    }

    private void initGameToStage(GamePageLocal gameLocalPage, PreviewType type) {
        Stage gameStage = new Stage();
        gameStage.setScene(new Scene(gameLocalPage.root));
        gameStage.setTitle("Local Game");
        gameStage.getIcons().add(Theme.getAppIcon());

        gameStage.setMinWidth(GamePageLocal.MIN_WIDTH);
        gameStage.setMinHeight(GamePageLocal.MIN_HEIGHT);
        gameStage.show();
        Platform.runLater(theme::registerGameBGM);

        if (type == PreviewType.LOAD_GAME || type == PreviewType.LOAD_GAME_FROM_FILE) {
            Alert alert = PromptLoader.getReplayGameAlert(theme);
            alert.showAndWait()
                    .filter(response -> response == ButtonType.YES)
                    .ifPresent(response -> {
                        new Thread(new Task<Void>() {
                            @Override
                            protected Void call() throws Exception {
                                controller.replayGame();
                                return null;
                            }
                        }).start();
                    });
        }
        gameLocalPage.updateElements();

        Log0j.writeInfo("LocalPlay (Load Game) initialized.");
        gameStage.setOnHidden(ActionEvent -> {
            gameLocalPage.performOnCloseAction();
            Platform.runLater(theme::unregisterGameBGM);
        });
    }

}
