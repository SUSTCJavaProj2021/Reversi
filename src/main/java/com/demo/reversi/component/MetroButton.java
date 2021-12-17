package com.demo.reversi.component;

import com.demo.reversi.themes.Theme;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

public class MetroButton extends Button {

    public static final double CORNER_RADII = 10;

    public static final int PREFERRED_HEIGHT = 40;
    public static final int PREFERRED_WIDTH = 167;

    public static final int FONT_SIZE = 14;
    public static final double ICON_GAP = 10;
    public static final double ICON_HEIGHT_RATIO = 0.52;

    public static final double OPACITY_DEFAULT = 0.6;
    public static final double OPACITY_PRESSED = 0.75;
    public static final double OPACITY_SELECTED = 1.0;

    public static final double TRANS_TIME_MILLIS = 100;

    //Prev Default Color: #1D1F2C
    //Prev Selected Color: #2A2C38
    //Prev Pressed Color: #262834

    private Theme theme;

    public MetroButton(String text, Theme theme) {
        super(text);
        this.theme = theme;

        setMinWidth(Button.USE_COMPUTED_SIZE);
        setMinHeight(Button.USE_COMPUTED_SIZE);
        setMaxWidth(Double.POSITIVE_INFINITY);
        setPrefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);


        theme.bindToMenuFontFamily(fontProperty());
        theme.bindToMenuFontPaint(textFillProperty());

        //Initialize default background
        setBackground(new Background(new BackgroundFill(Theme.defaultDarkModeColor, new CornerRadii(CORNER_RADII), null)));

        //Bind to background
        backgroundProperty().bind(Bindings.createObjectBinding(() -> {
            return new Background(new BackgroundFill(theme.getModePaintPR().getValue(), new CornerRadii(CORNER_RADII), null));
        }, theme.getModePaintPR()));

        //Unselected
        setOpacity(OPACITY_DEFAULT);

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setOpacity(OPACITY_SELECTED);
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setOpacity(OPACITY_DEFAULT);
            }
        });

        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setOpacity(OPACITY_PRESSED);
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setOpacity(OPACITY_SELECTED);
            }
        });
    }

    private MetroButton outer() {
        return this;
    }

}
