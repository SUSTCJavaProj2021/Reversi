package com.demo.reversi.component;

import com.demo.reversi.logger.Log0j;
import com.demo.reversi.themes.Theme;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

public class MetroButton extends Button {

    public static final double CORNER_RADII = 10;

    public static final int PREFERRED_HEIGHT = 40;
    public static final int PREFERRED_WIDTH = 75;

    public static final int FONT_SIZE = 14;
    public static final double ICON_GAP = 10;
    public static final double ICON_HEIGHT_RATIO = 0.52;

    public static double OPACITY_DEFAULT;
    public static double OPACITY_PRESSED;
    public static double OPACITY_SELECTED;

    public static final double TRANS_TIME_MILLIS = 100;

    //Prev Default Color: #1D1F2C
    //Prev Selected Color: #2A2C38
    //Prev Pressed Color: #262834

    private Theme theme;

    /**
     * Create a metro-styled button with default animations
     * Notice: setOnAction should be overridden by the user.
     *
     * @param text  Button text
     * @param theme The theme you are using
     */
    public MetroButton(String text, Theme theme) {
        super(text);
        this.theme = theme;
        setPrefSize(text.length() * 10 + 7, PREFERRED_HEIGHT);
        setMinWidth(Control.USE_COMPUTED_SIZE);
        setMinHeight(Control.USE_PREF_SIZE);
        setMaxWidth(Control.USE_PREF_SIZE);
        setTooltip(new Tooltip(text));

        fontProperty().bind(theme.menuFontFamilyPR());
        textFillProperty().bind(theme.modeRevPaintPR());

        //Initialize default background
        if (theme.modeSwitchPR().getValue()) {
            OPACITY_DEFAULT = 0.90;
            OPACITY_SELECTED = 0.85;
            OPACITY_PRESSED = 1.0;
            setBackground(new Background(new BackgroundFill(Theme.defaultDarkModeColor, new CornerRadii(CORNER_RADII), null)));
        } else {
            OPACITY_DEFAULT = 1.0;
            OPACITY_SELECTED = 0.85;
            OPACITY_PRESSED = 0.75;
            setBackground(new Background(new BackgroundFill(Theme.defaultLightModeColor, new CornerRadii(CORNER_RADII), null)));
        }

        //Bind to background
        backgroundProperty().bind(Bindings.createObjectBinding(() -> {
            if (theme.modeSwitchPR().getValue()) {
                OPACITY_DEFAULT = 1.0;
                OPACITY_SELECTED = 0.80;
                OPACITY_PRESSED = 1.0;
                return new Background(new BackgroundFill(Theme.defaultDarkModeColor, new CornerRadii(CORNER_RADII), null));
            } else {
                OPACITY_DEFAULT = 1.0;
                OPACITY_SELECTED = 0.85;
                OPACITY_PRESSED = 0.75;
                return new Background(new BackgroundFill(Theme.defaultLightModeColor, new CornerRadii(CORNER_RADII), null));
            }
        }, theme.modeSwitchPR()));

        //Initialize border
        borderProperty().bind(Bindings.createObjectBinding(() -> {
            Paint paint;
            if (theme.modeSwitchPR().getValue()) {
                paint = Color.rgb(53, 53, 53);
            } else {
                paint = Color.rgb(229, 229, 229);
            }
            return new Border(new BorderStroke(paint, BorderStrokeStyle.SOLID, new CornerRadii(CORNER_RADII), new BorderWidths(2)));
        }, theme.modeSwitchPR()));


        //Unselected
        setOpacity(OPACITY_DEFAULT);
        init();
    }

    private void init() {
        setOnMouseEntered(event -> {
            FadeTransition ft = new FadeTransition(Duration.millis(TRANS_TIME_MILLIS), outer());
            ft.setFromValue(OPACITY_DEFAULT);
            ft.setToValue(OPACITY_SELECTED);
            ft.setCycleCount(1);
            Platform.runLater(ft::play);
        });

        setOnMouseExited(event -> {
            FadeTransition ft = new FadeTransition(Duration.millis(TRANS_TIME_MILLIS), outer());
            ft.setFromValue(OPACITY_SELECTED);
            ft.setToValue(OPACITY_DEFAULT);
            ft.setCycleCount(1);
            Platform.runLater(ft::play);
        });

        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setOpacity(OPACITY_PRESSED);
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setOpacity(OPACITY_SELECTED);
            }
        });

    }

    private MetroButton outer() {
        return this;
    }

}
