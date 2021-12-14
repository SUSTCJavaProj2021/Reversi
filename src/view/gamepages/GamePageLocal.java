package view.gamepages;

import component.gamemodel.ChessBoard;
import component.panes.InfoPane;
import component.panes.ScorePane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import controller.SingleGameController;

public class GamePageLocal {
    public static final double MIN_WIDTH = InfoPane.MIN_WIDTH * 2 + ChessBoard.BOARD_SIZE;
    public static final double MIN_HEIGHT = ChessBoard.BOARD_SIZE + ScorePane.MIN_HEIGHT;

    public final BorderPane root;

    public final ChessBoard chessBoard;
    public final ScorePane scorePane;
    public final InfoPane whitePlayerInfoPane;
    public final InfoPane blackPlayerInfoPane;

    public GamePageLocal(SingleGameController controller) {
        chessBoard = new ChessBoard(controller);
        root = new BorderPane();

        whitePlayerInfoPane = new InfoPane(controller.getWhitePlayer());
        blackPlayerInfoPane = new InfoPane(controller.getBlackPlayer());

        root.setLeft(whitePlayerInfoPane);
        root.setRight(blackPlayerInfoPane);


        scorePane = new ScorePane(controller);
        root.setTop(scorePane);

        //TEST CHEAT MODE
        {
            ToggleButton toggleButton = new ToggleButton("Test");
            Button btn = new Button("SetCheat!");
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    controller.setCheatMode(true);
                }
            });
            root.setBottom(btn);
        }

        root.setCenter(chessBoard);

        root.setBackground(new Background(
                new BackgroundFill(Color.web("1C202C"), null, null)));


        //Set Auto-sized Columns
//        ColumnConstraints columnConstraints[] = new ColumnConstraints[2];
//        RowConstraints rowConstraints[] = new RowConstraints[2];
//        for (int i = 0; i < 2; i++) {
//            columnConstraints[i] = new ColumnConstraints(5, Control.USE_COMPUTED_SIZE,
//                    Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true);
//            rowConstraints[i] = new RowConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY,
//                    Priority.ALWAYS, VPos.CENTER, true);
//            gamePane.getColumnConstraints().add(i, columnConstraints[i]);
//            gamePane.getRowConstraints().add(i, rowConstraints[i]);
//        }
//        columnConstraints[0].setPercentWidth(25);
//        columnConstraints[1].setPercentWidth(75);
//        rowConstraints[0].setPercentHeight(8);
//        rowConstraints[1].setPercentHeight(92);
    }
}
