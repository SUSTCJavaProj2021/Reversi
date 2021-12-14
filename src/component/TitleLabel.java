package component;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TitleLabel extends Label {
    public static final double FONT_SIZE = 25;
    public static final double PREFERRED_HEIGHT = 50;

    public TitleLabel(String title) {
        super(title);
        setFont(new Font("Constantia", FONT_SIZE));
        setTextFill(Color.WHITE);
        setMinHeight(PREFERRED_HEIGHT);
        setPrefHeight(PREFERRED_HEIGHT);
    }
}
