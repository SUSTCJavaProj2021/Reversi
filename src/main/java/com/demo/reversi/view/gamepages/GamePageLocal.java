package com.demo.reversi.view.gamepages;

import com.demo.reversi.component.gamemodel.ChessBoard;
import com.demo.reversi.component.panes.ExtendedInfoPane;
import com.demo.reversi.component.panes.ScorePane;
import com.demo.reversi.controller.GameControllerLayer;
import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.Updatable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;

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
        Button btn = new Button("SetCheat!");

        root.setTop(scorePane);
        BorderPane.setAlignment(scorePane, Pos.CENTER);

        //TEST CHEAT MODE
        settingsPane = new GridPane();
        ToggleButton toggleButton = new ToggleButton("Test");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.setCheatMode(true);
            }
        });
        settingsPane.add(btn, 0, 0);

        settingsPane.getColumnConstraints().add(new ColumnConstraints(0, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
        //Set up settings pane
        root.setBottom(settingsPane);
        BorderPane.setAlignment(settingsPane, Pos.CENTER);


        //Last node added is on top
        root.setCenter(chessBoard);
        theme.bindToBackPane(root.backgroundProperty());
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
