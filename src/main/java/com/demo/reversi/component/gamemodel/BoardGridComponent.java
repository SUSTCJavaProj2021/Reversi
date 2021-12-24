package com.demo.reversi.component.gamemodel;

import com.demo.reversi.logger.Log0j;
import com.demo.reversi.themes.Theme;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.Stack;

public class BoardGridComponent extends StackPane {
    public static final double CORNER_RADII = 0;

    public static final double OPACITY_UNSELECTED = 1.0;
    public static final double OPACITY_PRESSED = 0.75;
    public static final double OPACITY_SELECTED = 0.7;

    public static final double TRANS_TIME_MILLIS = 100;


    public final StackPane viewCover;

    private final Theme theme;

    public BoardGridComponent(Theme theme) {
        setCache(true);
        this.theme = theme;
        viewCover = new StackPane();
        getChildren().add(viewCover);


//        viewCover.setBackground(new Background(new BackgroundFill(Color.rgb(255,123,238,0.5), null, null)));
//        viewCover.setBorder(new Border(new BorderStroke(Color.GREEN,BorderStrokeStyle.SOLID, null, new BorderWidths(5))));

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FadeTransition ft = new FadeTransition(Duration.millis(TRANS_TIME_MILLIS), outer());
                ft.setFromValue(OPACITY_UNSELECTED);
                ft.setToValue(OPACITY_SELECTED);
                ft.setCycleCount(1);
                Platform.runLater(ft::play);
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FadeTransition ft = new FadeTransition(Duration.millis(TRANS_TIME_MILLIS), outer());
                ft.setFromValue(OPACITY_SELECTED);
                ft.setToValue(OPACITY_UNSELECTED);
                ft.setCycleCount(1);
                Platform.runLater(ft::play);
            }
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
                try {
                    AudioClip clickSound = new AudioClip(theme.chessDownSoundSourcePR().getValue().toUri().toString());
                    clickSound.setVolume(theme.getEffectVolume());
                    clickSound.play();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log0j.writeInfo("Not able to play chess sound.");
                }
            }
        });

    }

    public BoardGridComponent outer() {
        return this;
    }

    public void setDefault() {
        Platform.runLater(() -> viewCover.getChildren().clear());
        viewCover.setBackground(null);
    }

    public void setPreferred() {
        Circle circle = new Circle();
        circle.radiusProperty().bind(widthProperty().multiply(0.2));
        circle.fillProperty().bind(theme.modePaintPR());
        viewCover.getChildren().add(circle);
//        viewCover.setBackground(new Background(new BackgroundFill(Color.rgb(5, 245, 240, 0.35), null, null)));
    }

    public void setBanned() {
        viewCover.setBackground(new Background(new BackgroundFill(Color.rgb(120, 120, 120, 0.5), null, null)));
    }

}
