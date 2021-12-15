package component.panes;

import controller.SingleGameController;
import javafx.geometry.HPos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import view.Theme;


public class ScorePane extends GridPane {
    public static final int MIN_HEIGHT = 45;
    public static final int PREF_HEIGHT = 70;

    private Label playerName[];
    private Label curPlayerLabel;

    public Theme theme;


    public ScorePane(SingleGameController controller, Theme theme) {
        super();
        this.theme = theme;
        theme.bindPaintBackground(backgroundProperty());
        this.setMinHeight(MIN_HEIGHT);
        this.setPrefHeight(PREF_HEIGHT);

        //Initialize Player Label
        playerName = new Label[2];
        for(int i = 0; i < 2; i++){
            playerName[i] = new Label();
//            theme.bindTextFontFamily(playerName[i].fontProperty());
            theme.bindTextFontPaint(playerName[i].textFillProperty());
        }
        playerName[0].textProperty().bind(controller.getWhitePlayer().nameProperty);
        playerName[1].textProperty().bind(controller.getBlackPlayer().nameProperty);

        this.add(playerName[0], 0, 0);
        this.add(playerName[1], 2, 0);

        GridPane.setHalignment(playerName[0], HPos.RIGHT);
        GridPane.setHalignment(playerName[1], HPos.LEFT);


        //TODO: add floating info panel to show the status of the player

        //Initialize Turn Indicator
        curPlayerLabel = new Label();
        curPlayerLabel.textProperty().bind(controller.curPlayerNameProperty);
//        theme.bindTextFontFamily(curPlayerLabel.fontProperty());
        theme.bindTextFontPaint(curPlayerLabel.textFillProperty());
        this.add(curPlayerLabel, 1, 0);


        //Set adaptive Layout
        ColumnConstraints[] columnConstraints = new ColumnConstraints[3];
        for (int i = 0; i < 3; i++) {
            columnConstraints[i] = new ColumnConstraints(5, Control.USE_COMPUTED_SIZE,
                    Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true);
            this.getColumnConstraints().add(i, columnConstraints[i]);
        }
        columnConstraints[0].setPercentWidth(35);
        columnConstraints[1].setPercentWidth(50);
        columnConstraints[2].setPercentWidth(35);
    }
}
