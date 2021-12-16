package view.gamepages;

import component.gamemodel.ChessBoard;
import component.panes.InfoPane;
import component.panes.ScorePane;
import controller.GameController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import view.Theme;
import view.Updatable;

public class GamePageLocal implements Updatable {
    public static final double MIN_WIDTH = InfoPane.MIN_WIDTH * 2 + ChessBoard.DEFAULT_BOARD_MIN_SIZE;
    public static final double MIN_HEIGHT = ChessBoard.DEFAULT_BOARD_MIN_SIZE + ScorePane.MIN_HEIGHT;

    public final BorderPane root;
    public final GameController controller;

    public final ChessBoard chessBoard;
    public final ScorePane scorePane;
    public final InfoPane whitePlayerInfoPane;
    public final InfoPane blackPlayerInfoPane;

    public Theme theme;

    public GamePageLocal(GameController controller, Theme theme) {
        this.theme = theme;
        this.controller = controller;
        root = new BorderPane();

        chessBoard = new ChessBoard(controller, theme);
        controller.bindToGamePage(this);
        BorderPane.setAlignment(chessBoard, Pos.CENTER);

        whitePlayerInfoPane = new InfoPane(controller.getWhitePlayer(), theme);
        blackPlayerInfoPane = new InfoPane(controller.getBlackPlayer(), theme);

        root.setLeft(whitePlayerInfoPane);
        root.setRight(blackPlayerInfoPane);


        scorePane = new ScorePane(controller, theme);
        Button btn = new Button("SetCheat!");

        root.setTop(scorePane);

        //TEST CHEAT MODE
        {
            ToggleButton toggleButton = new ToggleButton("Test");
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    controller.setCheatMode(true);
                }
            });
            root.setBottom(btn);
        }


        //Last node added is on top
        root.setCenter(chessBoard);
        theme.bindToBackPane(root.backgroundProperty());

    }

    @Override
    public void update() {
        chessBoard.update();
        scorePane.update();
    }

    public GamePageLocal outer() {
        return this;
    }
}
