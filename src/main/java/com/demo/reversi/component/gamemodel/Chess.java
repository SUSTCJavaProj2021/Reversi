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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * This Chess shall support three modes: Transparent Mode, Black Mode, White Mode.
 */
public class Chess extends StackPane implements Updatable {

    public enum ChessOwner {
        PLAYER1, PLAYER2, AI, PLACEHOLDER;
    }

    public static final double TRANS_TIME_MILLIS = 150;
    public static final Color placeHolderColor = Color.TRANSPARENT;

    public final Circle chessInnerCircle;
    public final Circle chessOuterCircle;

    public final DoubleProperty chessSizePR;
    public final ObjectProperty<Paint> playerPaint1PR;
    public final ObjectProperty<Paint> playerPaint2PR;
    public final ObjectProperty<ChessOwner> chessOwner;
    public boolean isChangeDirected;

    private final Theme theme;


    public Chess(double radius, Theme theme, ChessOwner chessOwner) {
        this.theme = theme;
        chessSizePR = new SimpleDoubleProperty(radius);
        chessInnerCircle = new Circle(radius * 0.75);
        chessOuterCircle = new Circle(radius);

        getChildren().addAll(chessOuterCircle, chessInnerCircle);
        StackPane.setAlignment(chessInnerCircle, Pos.CENTER);
        StackPane.setAlignment(chessOuterCircle, Pos.CENTER);
        /**
         * Binding chess properties
         */
        chessInnerCircle.radiusProperty().bind(chessSizePR);
        chessOuterCircle.radiusProperty().bind(chessSizePR);
//        chessOuterCircle.strokeWidthProperty().bind(chessSizePR.multiply(0.02));
//        chessOuterCircle.setFill(Color.TRANSPARENT);

//        chessOuterCircle.centerXProperty().bind(chessInnerCircle.centerXProperty().add(12));
//        chessOuterCircle.centerYProperty().bind(chessInnerCircle.centerYProperty().add(12));


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
//      getStyleClass().add("ring");

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
     * @param oldOwner Old owner of the chess
     * @param axis     rotation axis
     */
    public void update(ChessOwner oldOwner, Point3D axis) {
        switch (chessOwner.getValue()) {
            case PLAYER1:
                //Change fill
                chessInnerCircle.fillProperty().unbind();
                chessOuterCircle.fillProperty().unbind();
                if (oldOwner != ChessOwner.PLACEHOLDER && oldOwner != chessOwner.getValue()) {
                    Platform.runLater(() -> {
                        animateReverse(playerPaint1PR, playerPaint1PR, axis);
                    });
                } else {
                    chessInnerCircle.fillProperty().bind(playerPaint1PR);
                    chessOuterCircle.fillProperty().bind(playerPaint1PR);
                }
                break;

            case PLAYER2:
                //Change fill
                chessInnerCircle.fillProperty().unbind();
                chessOuterCircle.fillProperty().unbind();
                if (oldOwner != ChessOwner.PLACEHOLDER && oldOwner != chessOwner.getValue()) {
                    Platform.runLater(() -> {
                        animateReverse(playerPaint2PR, playerPaint2PR, axis);
                    });
                } else {
                    chessInnerCircle.fillProperty().bind(playerPaint2PR);
                    chessOuterCircle.fillProperty().bind(playerPaint2PR);
                }
                break;

            case PLACEHOLDER:
                chessInnerCircle.fillProperty().unbind();
                chessOuterCircle.fillProperty().unbind();
                chessInnerCircle.setFill(placeHolderColor);
                chessOuterCircle.setFill(placeHolderColor);
                break;

            case AI:
        }

        if (!chessOwner.getValue().equals(ChessOwner.PLACEHOLDER)) {
            DropShadow dropShadow = new DropShadow();
            dropShadow.setRadius(chessSizePR.doubleValue() + 5);
            dropShadow.setOffsetX(3.0);
            dropShadow.setOffsetY(3.0);
            dropShadow.setColor(Color.rgb(0, 0, 0, 0.45));
            chessOuterCircle.setEffect(dropShadow);
        } else {
            chessOuterCircle.setEffect(null);
        }
    }

    public void animateReverse(ObjectProperty<Paint> newPaint1, ObjectProperty<Paint> newPaint2, Point3D axis) {
        if (axis == null) {
            axis = new Point3D(1, 1, 0);
        }
        RotateTransition rotator1 = createRotatorUp(this, 1, axis);
        RotateTransition rotator2 = createRotatorUp(this, 2, axis);
        ScaleTransition scale1 = createScalar(this, 1);
        rotator1.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                chessInnerCircle.fillProperty().bind(newPaint1);
                chessOuterCircle.fillProperty().bind(newPaint2);
                rotator2.play();
            }
        });
        rotator2.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                /**
                 * Play chess dropping sound.
                 */
                try {
                    AudioClip clickSound = new AudioClip(theme.chessSoundSourcePR().getValue().toUri().toString());
                    clickSound.setVolume(theme.getEffectVolume());
                    clickSound.play();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log0j.writeLog("Not able to play chess sound.");
                }
            }
        });
        ParallelTransition pt = new ParallelTransition(rotator1, scale1);
        Platform.runLater(pt::play);
    }

    //todo: test
    private RotateTransition createRotatorUp(Node node, int para, Point3D axis) {
        RotateTransition rotator = new RotateTransition(Duration.millis(TRANS_TIME_MILLIS), node);
        rotator.setAxis(axis);
        rotator.setFromAngle((para - 1) * 90);
        rotator.setToAngle(para * 90);
        rotator.setInterpolator(new Interpolator() {
            @Override
            protected double curve(double t) {
                return Math.pow(t, 0.25);
            }
        });
        rotator.setCycleCount(1);
        return rotator;
    }

    private ScaleTransition createScalar(Node node, double multiple) {
        ScaleTransition scalar = new ScaleTransition(Duration.millis(TRANS_TIME_MILLIS), node);
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
