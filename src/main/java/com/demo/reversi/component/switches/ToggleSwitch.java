package com.demo.reversi.component.switches;

import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.Updatable;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class ToggleSwitch extends HBox implements Updatable {
    public static final double TRANS_TIME_MILLIS = 100;
    public static final double DEFAULT_WIDTH = 40;
    public static final double DEFAULT_HEIGHT = 20;
    public static final double CORNER_RADII = 10;
    public static final double INDICATOR_RADII = 6;
    public static final double BORDER_WIDTH = 1;

    private final Circle indicator;


    private final SimpleBooleanProperty switchedOn;

    public SimpleBooleanProperty switchedOnProperty() {
        return switchedOn;
    }

    private Theme theme;

    public ToggleSwitch(Theme theme) {
        this.theme = theme;
        switchedOn = new SimpleBooleanProperty(false);

        setMinWidth(DEFAULT_WIDTH);
        setMaxWidth(DEFAULT_WIDTH);
        setMinHeight(DEFAULT_HEIGHT);
        setMaxHeight(DEFAULT_HEIGHT);

        indicator = new Circle(INDICATOR_RADII);
        getChildren().add(indicator);
        HBox.setMargin(indicator, new Insets(CORNER_RADII - INDICATOR_RADII - BORDER_WIDTH));
//        indicator.setCenterX(15);
//        indicator.setCenterY(15);

        init();
    }

    private void init() {
        switchedOn.addListener((observable, oldValue, newValue) -> {
            TranslateTransition transition = new TranslateTransition(Duration.millis(TRANS_TIME_MILLIS), indicator);
            if (newValue) {
                transition.setByX(DEFAULT_WIDTH - 2 * CORNER_RADII);
            } else {
                transition.setByX(-DEFAULT_WIDTH + 2 * CORNER_RADII);
            }
            transition.setInterpolator(Interpolator.EASE_BOTH);
            Platform.runLater(transition::play);
        });

        indicator.fillProperty().bind(Bindings.createObjectBinding(() -> {
            if (switchedOn.getValue()) {
                return theme.modeColorPR().getValue();
            } else {
                return theme.modeRevColorPR().getValue();
            }
        }, switchedOn));

//        setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));

        backgroundProperty().bind(Bindings.createObjectBinding(() -> {
            if (switchedOn.getValue()) {
                return new Background(new BackgroundFill(theme.themeColorPR().getValue(), new CornerRadii(CORNER_RADII), null));
            } else {
                return null;
            }
        }, switchedOn, theme.themeColorPR()));

        borderProperty().bind(Bindings.createObjectBinding(() -> {
            if (switchedOn.getValue()) {
                return new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, new CornerRadii(CORNER_RADII), new BorderWidths(BORDER_WIDTH)));
            } else {
                return new Border(new BorderStroke(theme.modeRevColorPR().getValue(), BorderStrokeStyle.SOLID, new CornerRadii(CORNER_RADII), new BorderWidths(BORDER_WIDTH)));
            }
        }, switchedOn));


        setOnMouseClicked(MouseEvent -> {
            switchedOn.setValue(!switchedOn.getValue());
        });
    }

    public void update() {
        updateIndicator();
    }

    private void updateIndicator() {
    }

}
