package com.demo.reversi.view.gamepages;

import com.demo.reversi.component.gamemodel.ChessBoard;
import com.demo.reversi.controller.GameSystemLayer;
import com.demo.reversi.themes.Theme;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class TutorialPage {
    public final double DEFAULT_CHESS_BOARD_SIZE = 400;

    public final GridPane root;
    public final ChessBoard chessBoard;
    public TutorialPage(GameSystemLayer gameSystem, Theme theme){
        root = new GridPane();
        chessBoard = new ChessBoard(gameSystem.startNewGame("Test Player 1", "Test Player 2"), theme);
    }
}
