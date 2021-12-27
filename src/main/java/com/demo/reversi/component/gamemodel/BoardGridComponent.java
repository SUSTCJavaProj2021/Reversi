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

public class BoardGridComponent extends StackPane {
    public static final double CORNER_RADII = 0;

    public static final double OPACITY_UNSELECTED = 1.0;
    public static final double OPACITY_PRESSED = 0.75;
    public static final double OPACITY_SELECTED = 0.7;

    public static final double TRANS_TIME_MILLIS = 100;

    public final StackPane viewCover;

    private final Theme theme;



    private enum BoardStatus {
        DEFAULT, AVAILABLE, INVESTIGATING, BANNED;
    }

    private BoardStatus status;

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

        status = BoardStatus.DEFAULT;
    }

    public BoardGridComponent outer() {
        return this;
    }

    public void setDefault() {
        if (status != BoardStatus.DEFAULT) {
            Platform.runLater(() -> {
                viewCover.getChildren().clear();
                viewCover.backgroundProperty().unbind();
                viewCover.setBackground(null);
            });
            status = BoardStatus.DEFAULT;
        }
    }

    public void setAvailable() {
        if (status != BoardStatus.AVAILABLE) {
            setDefault();
            Circle circle = new Circle();
            circle.radiusProperty().bind(widthProperty().multiply(0.2));
            circle.fillProperty().bind(theme.chessBoardGridColorPR());
            Platform.runLater(() -> {
                viewCover.getChildren().add(circle);
            });
            status = BoardStatus.AVAILABLE;
        }
//        viewCover.setBackground(new Background(new BackgroundFill(Color.rgb(5, 245, 240, 0.35), null, null)));
    }

    public void setInvestigating() {
        if(status != BoardStatus.INVESTIGATING){
            Platform.runLater(() -> {
                setDefault();
                theme.bindToChessBoardInvestColor(viewCover.backgroundProperty());
            });
            status = BoardStatus.INVESTIGATING;
        }
    }

    public void setBanned() {
        if (status != BoardStatus.BANNED) {
            Platform.runLater(() -> {
                setDefault();
                theme.bindToChessBoardBannedColor(viewCover.backgroundProperty());
            });
            status = BoardStatus.BANNED;
        }
    }
}
