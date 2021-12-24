package com.demo.reversi.view.gamepages;

import com.demo.reversi.component.gamemodel.ChessBoard;
import com.demo.reversi.component.panes.InfoPane;
import com.demo.reversi.controller.GameControllerLayer;
import com.demo.reversi.controller.GameSystemLayer;
import com.demo.reversi.logger.Log0j;
import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.prompts.PromptLoader;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Optional;

public class GamePreviewPane extends GridPane {
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
    public static final double MIN_WIDTH = 250;
    public static final double CHESSBOARD_SIZE = 220;
    public static final double CHESSBOARD_MARGIN = 15;
    public static final double OPACITY_DEFAULT = 0.8;
    public static final double OPACITY_SELECTED = 1.0;
    public static final double OPACITY_PRESSED = 0.7;

    public final Theme theme;
    public final StackPane viewCover;
    public final InfoPane player1Info;
    public final InfoPane player2Info;
    public final Label gameStatusLabel;
    public final Label lastModifiedTimeLabel;
    public final Label createdTimeLabel;

    public final GameSystemLayer gameSystem;
    public final ChessBoard chessBoard;

    public GameControllerLayer controller;

    public enum PreviewType {
        NEW_GAME, LOAD_GAME_FROM_FILE, LOAD_GAME;
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
        this.gameSystem = gameSystem;
        this.controller = controller;
        this.theme = theme;

        viewCover = new StackPane();

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

        initRelations();
    }

    /**
     * This constructor is used for "New Game" creation and "Load Game from File" creation
     *
     * @param gameSystem GameSystem
     * @param theme      Theme
     */
    public GamePreviewPane(GameSystemLayer gameSystem, Theme theme, PreviewType previewType) {
        this.gameSystem = gameSystem;
        this.theme = theme;

        viewCover = new StackPane();

        chessBoard = new ChessBoard(theme, CHESSBOARD_SIZE);

        player1Info = new InfoPane(theme, theme.player1ChessColorPR());
        player2Info = new InfoPane(theme, theme.player2ChessColorPR());
        gameStatusLabel = new Label();
        createdTimeLabel = new Label();
        lastModifiedTimeLabel = new Label();


        if (previewType == PreviewType.NEW_GAME) {
            initLayout(PreviewType.NEW_GAME);
            initNewGameAction();
        } else {
            initLayout(PreviewType.LOAD_GAME_FROM_FILE);
            initLoadGameFromFileAction();
        }

        initRelations();
    }

    private void initRelations() {
        if (controller == null) {

        } else {

        }
    }

    private void initLayout(PreviewType type) {
        //todo: finish layout settings
        setPrefWidth(MIN_WIDTH);
        setMinHeight(MIN_HEIGHT);
        backgroundProperty().bind(theme.frontPanePR());

        add(chessBoard, 0, 0);
        GridPane.setConstraints(chessBoard, 0, 0, GridPane.REMAINING, 1,
                HPos.CENTER, VPos.CENTER, Priority.SOMETIMES, Priority.ALWAYS, new Insets(CHESSBOARD_MARGIN));


        player1Info.setPrefWidth(195);
        player2Info.setPrefWidth(195);
        add(player1Info, 0, 1);
        add(player2Info, 0, 2);

        add(gameStatusLabel, 1, 1, 1, 2);
        add(createdTimeLabel, 0, 3, GridPane.REMAINING, 1);
        add(lastModifiedTimeLabel, 0, 4, GridPane.REMAINING, 1);

        switch (type) {
            case NEW_GAME -> {

            }
            case LOAD_GAME -> {

            }
            case LOAD_GAME_FROM_FILE -> {

            }
        }
    }

    private void initAnimation() {
    }

    private void initLoadGameFromFileAction() {
        setOnMouseClicked(MouseEvent -> {
            //todo: finish this.
        });
    }


    private void initLoadGameAction() {
        setOnMouseClicked(MouseEvent -> {
            GamePageLocal gameLocalPage = new GamePageLocal(gameSystem, gameSystem.registerGamePlayable(controller), theme);
            initGameToStage(gameLocalPage);
        });
    }

    private void initNewGameAction() {
        setOnMouseClicked(MouseEvent -> {
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

            if (controller != null) {
                GamePageLocal gameLocalPage = new GamePageLocal(gameSystem, controller, theme);
                initGameToStage(gameLocalPage);
            } else {
                Log0j.writeInfo("Game loading failed for unknown reason. The scenario was considered not going to happen.");
            }
        });

    }

    private void initGameToStage(GamePageLocal gameLocalPage) {
        Stage gameStage = new Stage();
        gameStage.setScene(new Scene(gameLocalPage.root));
        gameStage.setTitle("Local Game");
        gameStage.getIcons().add(Theme.getAppIcon());

        gameStage.setMinWidth(GamePageLocal.MIN_WIDTH);
        gameStage.setMinHeight(GamePageLocal.MIN_HEIGHT);

        gameStage.show();
        Platform.runLater(theme::registerGameBGM);
        Log0j.writeInfo("LocalPlay (Load Game) initialized.");
        gameStage.setOnCloseRequest(ActionEvent -> {
            Platform.runLater(theme::unregisterGameBGM);
        });
    }

}
