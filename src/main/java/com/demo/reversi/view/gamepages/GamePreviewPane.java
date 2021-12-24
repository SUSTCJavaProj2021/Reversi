package com.demo.reversi.view.gamepages;

import com.demo.reversi.component.gamemodel.ChessBoard;
import com.demo.reversi.component.panes.InfoPane;
import com.demo.reversi.controller.GameControllerLayer;
import com.demo.reversi.controller.GameSystemLayer;
import com.demo.reversi.logger.Log0j;
import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.prompts.PromptLoader;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
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

    public static final double DEFAULT_MIN_HEIGHT = 500;
    public static final double DEFAULT_MIN_WIDTH = 300;

    public final Theme theme;
    public final InfoPane player1Info;
    public final InfoPane player2Info;
    public final Label gameStatusLabel;
    public final Label lastModifiedTimeLabel;
    public final Label createdTimeLabel;

    public final GameSystemLayer gameSystem;
    public final ChessBoard chessBoard;

    public GameControllerLayer controller;

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

        chessBoard = new ChessBoard(theme);
        chessBoard.initBoardDemo(controller);

        player1Info = new InfoPane(theme, theme.player1ChessColorPR());
        player2Info = new InfoPane(theme, theme.player2ChessColorPR());
        gameStatusLabel = new Label();
        createdTimeLabel = new Label();
        lastModifiedTimeLabel = new Label();

        initLayout();

        initLoadGameAction();
    }

    public enum PreviewType {
        NEW_GAME, LOAD_GAME_FROM_FILE, DEFAULT;
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

        chessBoard = new ChessBoard(theme);

        player1Info = new InfoPane(theme, theme.player1ChessColorPR());
        player2Info = new InfoPane(theme, theme.player2ChessColorPR());
        gameStatusLabel = new Label();
        createdTimeLabel = new Label();
        lastModifiedTimeLabel = new Label();

        initLayout();

        if (previewType == PreviewType.NEW_GAME) {
            initNewGameAction();
        } else {
            initLoadGameFromFileAction();
        }
    }

    private void initLayout(){
    }

    private void initLoadGameFromFileAction() {
        setOnMouseClicked(MouseEvent -> {
            //todo: finish this.
        });
    }


    private void initLoadGameAction() {
        setOnMouseClicked(MouseEvent -> {
            GamePageLocal gameLocalPage = new GamePageLocal(gameSystem, gameSystem.registerGamePlayable(controller), theme);
            initGame(gameLocalPage);
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
                initGame(gameLocalPage);
            } else {
                Log0j.writeInfo("Game loading failed for unknown reason.");
            }
        });

    }

    private void initGame(GamePageLocal gameLocalPage) {
        Stage gameStage = new Stage();
        gameStage.setScene(new Scene(gameLocalPage.root));
        gameStage.setTitle("Local Game");
        gameStage.getIcons().add(Theme.getAppIcon());

        gameStage.setMinWidth(GamePageLocal.MIN_WIDTH);
        gameStage.setMinHeight(GamePageLocal.MIN_HEIGHT);

        gameStage.show();
        Platform.runLater(theme::registerGame);
        Log0j.writeInfo("LocalPlay (Load Game) initialized.");
        gameStage.setOnCloseRequest(ActionEvent -> {
            Platform.runLater(theme::unregisterGame);
        });
    }

}
