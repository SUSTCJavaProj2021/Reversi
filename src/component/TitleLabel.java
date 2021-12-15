package component;

import javafx.scene.control.Label;
import view.Theme;

public class TitleLabel extends Label {
    public static final double FONT_SIZE = 25;
    public static final double PREFERRED_HEIGHT = 50;

    public TitleLabel(String title, Theme theme) {
        super(title);

        theme.bindTitleFontFamily(fontProperty());
        theme.bindTitleFontPaint(textFillProperty());

        setMinHeight(PREFERRED_HEIGHT);
        setPrefHeight(PREFERRED_HEIGHT);
    }
}
