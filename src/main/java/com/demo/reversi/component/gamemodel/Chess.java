package com.demo.reversi.component.gamemodel;

import com.demo.reversi.logger.Log0j;
import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.Updatable;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * This Chess shall support three modes: Transparent Mode, Black Mode, White Mode.
 */
public class Chess extends Circle implements Updatable {

    public enum ChessOwner {
        PLAYER1, PLAYER2, AI, PLACEHOLDER;
    }

    public static final Color placeHolderColor = Color.TRANSPARENT;


    public final DoubleProperty chessSizePR;
    public final ObjectProperty<Paint> playerPaint1PR;
    public final ObjectProperty<Paint> playerPaint2PR;
    public final ObjectProperty<ChessOwner> chessOwner;
    public boolean isChangeDirected;

    private final Theme theme;


    public Chess(double radius, Theme theme, ChessOwner chessOwner) {
        super(radius);
        this.theme = theme;
        chessSizePR = new SimpleDoubleProperty(radius);
        radiusProperty().bind(chessSizePR);

        playerPaint1PR = new SimpleObjectProperty<>();
        playerPaint2PR = new SimpleObjectProperty<>();

        isChangeDirected = false;

        this.chessOwner = new SimpleObjectProperty<>(chessOwner);

//        this.chessOwner.addListener(new ChangeListener<ChessOwner>() {
//            @Override
//            public void changed(ObservableValue<? extends ChessOwner> observable, ChessOwner oldValue, ChessOwner newValue) {
//                if (isChangeDirected) {
//                    update(oldValue);
//                }
//                else{
//                    update(oldValue);
//                }
//            }
//        });

        update(ChessOwner.PLACEHOLDER, null);
    }

    public Chess(double radius, Theme theme) {
        this(radius, theme, ChessOwner.PLACEHOLDER);
    }

    public void setChessOwner(ChessOwner owner) {
        ChessOwner oldValue = chessOwner.getValue();
        chessOwner.setValue(owner);
        Platform.runLater(() -> {
            update(oldValue, null);
        });
    }

    public void setChessOwnerDirected(ChessOwner owner, int actualRowDir, int actualColDir) {
        ChessOwner oldValue = chessOwner.getValue();
        chessOwner.setValue(owner);
        update(oldValue, new Point3D(-actualColDir, actualRowDir, 0));
    }

    @Override
    public void update() {

    }

    /**
     * @param oldOwner
     * @param axis     rotation axis
     */
    public void update(ChessOwner oldOwner, Point3D axis) {
        switch (chessOwner.getValue()) {
            case PLAYER1:
                fillProperty().unbind();
                if (oldOwner != ChessOwner.PLACEHOLDER && oldOwner != chessOwner.getValue()) {
                    Platform.runLater(() -> {
                        animateReverse(playerPaint1PR, axis);
                    });
                } else {
                    fillProperty().bind(playerPaint1PR);
                }
                break;

            case PLAYER2:
                fillProperty().unbind();
                if (oldOwner != ChessOwner.PLACEHOLDER && oldOwner != chessOwner.getValue()) {
                    Platform.runLater(() -> {
                        animateReverse(playerPaint2PR(), axis);
                    });
                } else {
                    fillProperty().bind(playerPaint2PR);
                }
                break;

            case PLACEHOLDER:
                fillProperty().unbind();
                setFill(placeHolderColor);
                break;

            case AI:
        }

        if (!chessOwner.getValue().equals(ChessOwner.PLACEHOLDER)) {
            DropShadow dropShadow = new DropShadow();
            dropShadow.setRadius(radiusProperty().doubleValue() + 5);
            dropShadow.setOffsetX(3.0);
            dropShadow.setOffsetY(3.0);
            dropShadow.setColor(Color.rgb(0, 0, 0, 0.45));
            setEffect(dropShadow);
        } else {
            setEffect(null);
        }
    }

    public void animateReverse(ObjectProperty<Paint> newPaint, Point3D axis) {
        if (axis == null) {
            axis = new Point3D(1, 1, 0);
        }
        RotateTransition rotator1 = createRotatorUp(this, 1, axis);
        RotateTransition rotator2 = createRotatorUp(this, 2, axis);
        ScaleTransition scale1 = createScalar(this, 1.001);
        rotator1.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fillProperty().bind(newPaint);
                rotator2.play();
            }
        });
        rotator2.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    AudioClip clickSound = new AudioClip(theme.getChessSoundSource().getValue().toUri().toString());
                    clickSound.setVolume(theme.getEffectVolume());
                    clickSound.play();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log0j.writeLog("Not able to play chess sound.");
                }
            }
        });
        ParallelTransition pt = new ParallelTransition(rotator1,scale1);
        pt.play();
    }

    //todo: test
    private RotateTransition createRotatorUp(Node node, int para, Point3D axis) {
        RotateTransition rotator = new RotateTransition(Duration.millis(200), node);
        rotator.setAxis(axis);
        rotator.setFromAngle((para - 1) * 180);
        rotator.setToAngle(para * 180);
        rotator.setInterpolator(Interpolator.LINEAR);
        rotator.setCycleCount(1);
        return rotator;
    }

    private ScaleTransition createScalar(Node node, double multiple) {
        ScaleTransition scalar = new ScaleTransition(Duration.millis(200), node);
        scalar.setByX(multiple);
        scalar.setByY(multiple);
        scalar.setCycleCount(2);
        scalar.setInterpolator(Interpolator.LINEAR);
        scalar.setAutoReverse(true);
        return scalar;
    }

    public ObjectProperty<Paint> playerPaint1PR() {
        return playerPaint1PR;
    }

    public ObjectProperty<Paint> playerPaint2PR() {
        return playerPaint2PR;
    }


}
