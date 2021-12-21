package com.demo.reversi.component.gamemodel;

import com.demo.reversi.logger.Log0j;
import com.demo.reversi.themes.Theme;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.*;
import javafx.util.Duration;

public class BoardGridComponent extends StackPane {
    public static final double CORNER_RADII = 0;

    public static final double OPACITY_UNSELECTED = 1.0;
    public static final double OPACITY_PRESSED = 0.75;
    public static final double OPACITY_SELECTED = 0.7;

    public static final double TRANS_TIME_MILLIS = 100;

    private final Theme theme;

    public BoardGridComponent(Theme theme){
        super();
        this.theme = theme;

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FadeTransition ft = new FadeTransition(Duration.millis(TRANS_TIME_MILLIS), outer());
                ft.setFromValue(OPACITY_UNSELECTED);
                ft.setToValue(OPACITY_SELECTED);
                ft.setCycleCount(1);
                Platform.runLater(()->{
                        ft.play();
                });
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FadeTransition ft = new FadeTransition(Duration.millis(TRANS_TIME_MILLIS), outer());
                ft.setFromValue(OPACITY_SELECTED);
                ft.setToValue(OPACITY_UNSELECTED);
                ft.setCycleCount(1);
                Platform.runLater(()->{
                        ft.play();
                });
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
                try{
                    AudioClip clickSound = new AudioClip(theme.chessDownSoundSourcePR().getValue().toUri().toString());
                    clickSound.setVolume(theme.getEffectVolume());
                    clickSound.play();
                }catch (Exception e){
                    e.printStackTrace();
                    Log0j.writeLog("Not able to play chess sound.");
                }
            }
        });
    }

    public BoardGridComponent outer(){
        return this;
    }

}
