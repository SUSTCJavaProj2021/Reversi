package com.demo.reversi.component.panes;

import com.demo.reversi.controller.interfaces.PlayerLayer;
import com.demo.reversi.themes.Theme;
import javafx.geometry.HPos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class ExtendedInfoPane extends GridPane {
    public static final int MIN_WIDTH = 200;

    public Theme theme;

    public ExtendedInfoPane(PlayerLayer player, Theme theme) {
        super();
        this.theme = theme;
        this.setMinWidth(MIN_WIDTH);

        Label nameLabel = new Label();
        nameLabel.textProperty().bind(player.nameProperty());
        nameLabel.setWrapText(true);
        nameLabel.fontProperty().bind(theme.textFontFamilyPR());
        nameLabel.textFillProperty().bind(theme.textFontPaintPR());

        this.add(nameLabel, 0, 0);
        this.getColumnConstraints().add(new ColumnConstraints(0, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.SOMETIMES, HPos.CENTER, true));
        GridPane.setHgrow(this, Priority.SOMETIMES);

        backgroundProperty().bind(theme.frontPanePR());
    }

}
