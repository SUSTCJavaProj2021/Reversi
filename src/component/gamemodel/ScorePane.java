package component.gamemodel;

import controller.GameController;
import javafx.geometry.HPos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


public class ScorePane extends GridPane {
    public static final int MIN_HEIGHT = 45;
    public static final int PREF_HEIGHT = 60;

    public static final Color backgroundColor = Color.LIGHTGRAY;
    public static final Background background = new Background(new BackgroundFill(backgroundColor, null, null));

    private Label playerName[];
    private Label curPlayerLabel;


    public ScorePane(GameController controller) {
        super();
        this.setMinHeight(MIN_HEIGHT);
        this.setPrefHeight(PREF_HEIGHT);
        this.setBackground(background);

        //Initialize Player Label
        playerName = new Label[2];
        //This method is supposed to take the Player object rather than String
        for (int i = 0; i < 2; i++) {
            playerName[i] = new Label("Player" + (i + 1));
        }
        this.add(playerName[0], 0, 0);
        this.add(playerName[1], 2, 0);
        GridPane.setHalignment(playerName[0], HPos.RIGHT);
        GridPane.setHalignment(playerName[1], HPos.LEFT);
        //TODO: add floating info panel to show the status of the player

        //Initialize Turn Indicator
        curPlayerLabel = new Label();
        curPlayerLabel.textProperty().bind(controller.getGameSystem().getCurPlayerProperty());
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
