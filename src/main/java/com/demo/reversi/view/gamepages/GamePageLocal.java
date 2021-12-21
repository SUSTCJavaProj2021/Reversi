package com.demo.reversi.view.gamepages;

import com.demo.reversi.component.MetroButton;
import com.demo.reversi.component.gamemodel.ChessBoard;
import com.demo.reversi.component.panes.ExtendedInfoPane;
import com.demo.reversi.component.panes.ScorePane;
import com.demo.reversi.controller.GameControllerLayer;
import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.Updatable;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GamePageLocal implements Updatable {
    public static final double MIN_WIDTH = ExtendedInfoPane.MIN_WIDTH * 2 + ChessBoard.DEFAULT_BOARD_MIN_SIZE;
    public static final double MIN_HEIGHT = ChessBoard.DEFAULT_BOARD_MIN_SIZE + ScorePane.MIN_HEIGHT;
    public static final double DEFAULT_PREF_WIDTH = 915;
    public static final double DEFAULT_PREF_HEIGHT = 611;

    public final BorderPane root;
    public final GameControllerLayer controller;

    public final ChessBoard chessBoard;
    public final ScorePane scorePane;
    public final GridPane settingsPane;

    public Theme theme;

    public GamePageLocal(GameControllerLayer controller, Theme theme) {
        this.theme = theme;
        this.controller = controller;
        root = new BorderPane();

        chessBoard = new ChessBoard(controller, theme);
        controller.bindToGamePage(this);
        BorderPane.setAlignment(chessBoard, Pos.CENTER);


        scorePane = new ScorePane(controller, theme);

        root.setTop(scorePane);
        BorderPane.setAlignment(scorePane, Pos.CENTER);

        //TEST CHEAT MODE
        settingsPane = new GridPane();
        settingsPane.setMinHeight(300);

        MetroButton btn = new MetroButton("Perform board judge!", theme);
        btn.setPrefHeight(300);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                chessBoard.curtainCall();
                Platform.runLater(() -> {
                    theme.bgmPlayerInterrupt(100);
                });
                Stage stage = new Stage();
                stage.setScene(new Scene(new Button("Well played!")));
                stage.show();
                stage.setOnCloseRequest(ActionEvent -> {
                    Platform.runLater(() -> {
                        theme.bgmPlayerResume(100);
                    });
                });
            }
        });
        settingsPane.add(btn, 0, 0);

        settingsPane.getColumnConstraints().add(new ColumnConstraints(0, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
        //Set up settings pane
        root.setBottom(settingsPane);
        BorderPane.setAlignment(settingsPane, Pos.CENTER);


        //Last node added is on top
        root.setCenter(chessBoard);
        root.backgroundProperty().bind(theme.backPanePR());
        root.setPrefWidth(DEFAULT_PREF_WIDTH);
        root.setPrefHeight(DEFAULT_PREF_HEIGHT);

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
        scorePane.update();
        //todo: add updates
    }


    public GamePageLocal outer() {
        return this;
    }
}
