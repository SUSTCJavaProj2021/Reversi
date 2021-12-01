package view;

import component.gamemodel.ChessBoard;
import component.gamemodel.InfoPane;
import component.gamemodel.ScorePane;
import controller.Player;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import controller.GameController;

public class GamePageLocal {
    public Scene scene;
    public BorderPane rootPane;

    public ChessBoard chessBoard;

    public GamePageLocal(GameController controller) {
        chessBoard = new ChessBoard(controller);
        rootPane = new BorderPane();

        rootPane.setLeft(new InfoPane(new Player()));

        // BEGINNING OF TEMPORARY TEST
        ScorePane sc = new ScorePane(controller);
        rootPane.setTop(sc);
        // END OF TEMPORARY TEST

        rootPane.setCenter(chessBoard);


        rootPane.setBackground(new Background(
                new BackgroundFill(Color.BLACK, null, null)));


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

        scene = new Scene(rootPane);
    }
}
