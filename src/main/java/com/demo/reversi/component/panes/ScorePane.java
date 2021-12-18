package com.demo.reversi.component.panes;

import com.demo.reversi.controller.GameController;
import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.Updatable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;


public class ScorePane extends GridPane implements Updatable {
    public static final int MIN_HEIGHT = 65;
    public static final int PREF_HEIGHT = 100;

    private final Label curPlayerLabel;

    public final GameController controller;

    private final Theme theme;


    public ScorePane(GameController controller, Theme theme) {
        super();
        this.controller = controller;
        this.theme = theme;
//        theme.bindToPaintBackground(backgroundProperty());

        setMinHeight(MIN_HEIGHT);
        setPrefHeight(PREF_HEIGHT);

        //todo: add floating info panel to show the status of the player

        //Initialize Turn Indicator
        curPlayerLabel = new Label();
        curPlayerLabel.setText(controller.getCurrentPlayer().nameProperty().getValue());
        curPlayerLabel.setTextAlignment(TextAlignment.CENTER);
        curPlayerLabel.setAlignment(Pos.CENTER);
//        theme.bindTextFontFamily(curPlayerLabel.fontProperty());
        theme.bindToTextFontPaint(curPlayerLabel.textFillProperty());
        theme.bindToTitleFontFamily(curPlayerLabel.fontProperty());
        add(curPlayerLabel, 1, 0);

//        curPlayerLabel.setBackground(new Background(new BackgroundFill(Color.GREEN,null,null)));

//        GridPane.setHgrow(this, Priority.ALWAYS);

        //todo: change player color settings
        StackPane player1Indicator = new StackPane();
        player1Indicator.setMinWidth(50);
        player1Indicator.setBackground(new Background(new BackgroundFill(Color.BLACK,new CornerRadii(15),null)));
        StackPane player2Indicator = new StackPane();
        player2Indicator.setMinWidth(50);
        player2Indicator.setBackground(new Background(new BackgroundFill(Color.WHITE,new CornerRadii(15),null)));
        add(player1Indicator, 0, 0);
        GridPane.setVgrow(player1Indicator, Priority.SOMETIMES);
        add(player2Indicator, 2, 0);
        GridPane.setVgrow(player2Indicator, Priority.SOMETIMES);

        ColumnConstraints cs = new ColumnConstraints(0, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.SOMETIMES, HPos.CENTER, true);
        getColumnConstraints().add(new ColumnConstraints());
        getColumnConstraints().add(cs);
        getColumnConstraints().add(new ColumnConstraints());
    }

    @Override
    public void update() {
        curPlayerLabel.setText(controller.getCurrentPlayer().nameProperty().getValue());
    }
}
