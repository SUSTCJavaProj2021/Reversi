package com.demo.reversi.component.selector;

import com.demo.reversi.themes.Theme;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class SelectorButton extends Button {

    public static final double CORNER_RADII = 10;

    public static final int PREFERRED_HEIGHT = 40;
    public static final int PREFERRED_WIDTH = 167;

    public static final int FONT_SIZE = 14;
    public static final double ICON_GAP = 10;
    public static final double ICON_HEIGHT_RATIO = 0.52;

    public static final double OPACITY_UNSELECTED = 0.4;
    public static final double OPACITY_PRESSED = 0.75;
    public static final double OPACITY_SELECTED = 1.0;

    public static final double TRANS_TIME_MILLIS = 100;

    //Prev Default Color: #1D1F2C
    //Prev Selected Color: #2A2C38
    //Prev Pressed Color: #262834

    public static final Background defaultBackground = new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(CORNER_RADII), null));
    public static final Background selectedBackground = new Background(new BackgroundFill(Color.rgb(255, 255, 255, 0.08), new CornerRadii(CORNER_RADII), null));

    public boolean isSelected;

    private Theme theme;
    private Node node;

    public enum State {
        DEFAULT, ONMOUSE, SELECTED;
    }

    public SelectorButton(String text, Node node, ImageView icon, Theme theme) {
        super(text);
        this.theme = theme;
        this.node = node;
        isSelected = false;

        setMinWidth(Button.USE_COMPUTED_SIZE);
        setMinHeight(Button.USE_COMPUTED_SIZE);
        setMaxWidth(Double.POSITIVE_INFINITY);
        setPrefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
        setBackground(defaultBackground);

        setAlignment(Pos.BASELINE_LEFT);
        theme.bindToMenuFontFamily(fontProperty());
        theme.bindToMenuFontPaint(textFillProperty());

        if (icon != null) {
            setGraphic(icon);
            icon.setFitHeight(PREFERRED_HEIGHT * ICON_HEIGHT_RATIO);
            setGraphicTextGap(ICON_GAP);
        }


        //Unselected
        setOpacity(OPACITY_UNSELECTED);

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!isSelected) {
                    setBackground(selectedBackground);
                    FadeTransition ft = new FadeTransition(Duration.millis(TRANS_TIME_MILLIS), outer());
                    ft.setFromValue(OPACITY_UNSELECTED);
                    ft.setToValue(OPACITY_SELECTED);
                    ft.setCycleCount(1);
                    ft.play();
                }
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!isSelected) {
                    setBackground(defaultBackground);
                    FadeTransition ft = new FadeTransition(Duration.millis(TRANS_TIME_MILLIS), outer());
                    ft.setFromValue(OPACITY_SELECTED);
                    ft.setToValue(OPACITY_UNSELECTED);
                    ft.setCycleCount(1);
                    ft.play();
                }
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

    public void setSelected() {
        isSelected = true;
        node.setVisible(true);
        FadeTransition fadeIn = createFadeIn(node);
        TranslateTransition slideUp = createTranslateSlideUp(node);
        Platform.runLater(() -> {
            fadeIn.play();
            slideUp.play();
        });

        setBackground(selectedBackground);
        setOpacity(OPACITY_SELECTED);
    }

    private FadeTransition createFadeIn(Node node) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(100), node);
        fadeOut.setFromValue(0.5);
        fadeOut.setToValue(1.0);
        fadeOut.setInterpolator(Interpolator.LINEAR);
        return fadeOut;
    }

    private TranslateTransition createTranslateSlideUp(Node node) {
        TranslateTransition slideUp = new TranslateTransition(Duration.millis(100), node);
        slideUp.setFromY(node.getLayoutY() + 100);
        slideUp.setToY(node.getLayoutY());
        slideUp.setInterpolator(new Interpolator() {
            @Override
            protected double curve(double t) {
                return Math.pow(t, 0.25);
            }
        });
        return slideUp;
    }

    public void setDeselected() {
        isSelected = false;

        FadeTransition fadeOut = createFadeOut(node);
        Platform.runLater(() -> {
            fadeOut.play();
            node.setVisible(false);
        });

        setBackground(defaultBackground);
        FadeTransition ft = new FadeTransition(Duration.millis(TRANS_TIME_MILLIS), outer());
        ft.setFromValue(OPACITY_SELECTED);
        ft.setToValue(OPACITY_UNSELECTED);
        ft.play();
    }

    private FadeTransition createFadeOut(Node node) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(100), node);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setInterpolator(Interpolator.LINEAR);
        return fadeOut;
    }


    private SelectorButton outer() {
        return this;
    }

}
