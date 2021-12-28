package com.demo.reversi.component.gamemodel;

import com.demo.reversi.logger.Log0j;
import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.Updatable;
import javafx.animation.*;
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

    public static final double TRANS_TIME_MILLIS = 100;
    public static final Color placeHolderColor = Color.TRANSPARENT;

    public final Circle chessInnerCircle;

    public final DoubleProperty chessSizePR;
    public final ObjectProperty<Paint> playerPaint1PR;
    public final ObjectProperty<Paint> playerPaint2PR;
    public final ObjectProperty<ChessOwner> chessOwner;
    public boolean isChangeDirected;

    private final Theme theme;


    public Chess(double radius, Theme theme, ChessOwner chessOwner) {
        setCache(true);

        this.theme = theme;
        chessSizePR = new SimpleDoubleProperty(radius);
        chessInnerCircle = new Circle(radius);

        getChildren().addAll(chessInnerCircle);
        StackPane.setAlignment(chessInnerCircle, Pos.CENTER);
        /**
         * Binding chess properties
         */
        chessInnerCircle.radiusProperty().bind(chessSizePR);
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

        update(ChessOwner.PLACEHOLDER, null, true);
    }

    public Chess(double radius, Theme theme) {
        this(radius, theme, ChessOwner.PLACEHOLDER);
    }

    public void setChessOwner(ChessOwner owner) {
        ChessOwner oldValue = chessOwner.getValue();
        chessOwner.setValue(owner);
        Platform.runLater(() -> {
            update(oldValue, null, false);
        });
    }

    public void setChessOwnerDirected(ChessOwner owner, int actualRowDir, int actualColDir) {
        ChessOwner oldValue = chessOwner.getValue();
        chessOwner.setValue(owner);
        Platform.runLater(() -> {
            update(oldValue, new Point3D(-actualColDir, actualRowDir, 0), false);
        });
    }

    public void setChessOwnerForceAnimated(ChessOwner owner) {
        ChessOwner oldValue = chessOwner.getValue();
        chessOwner.setValue(owner);
        Platform.runLater(() -> {
            update(oldValue, null, true);
        });
    }

    @Override
    public void update() {

    }

    @Override
    public void performOnCloseAction() {

    }

    /**
     * @param oldOwner Old owner of the chess
     * @param axis     rotation axis
     */
    public void update(ChessOwner oldOwner, Point3D axis, boolean forceAnimation) {
        switch (chessOwner.getValue()) {
            case PLAYER1:
                //Change fill
                chessInnerCircle.fillProperty().unbind();
                if (forceAnimation || oldOwner != ChessOwner.PLACEHOLDER && oldOwner != chessOwner.getValue()) {
                    Platform.runLater(() -> {
                        animateReverse(playerPaint1PR, axis);
                    });
                } else {
                    chessInnerCircle.fillProperty().bind(playerPaint1PR);
                }
                break;

            case PLAYER2:
                //Change fill
                chessInnerCircle.fillProperty().unbind();
                if (forceAnimation || oldOwner != ChessOwner.PLACEHOLDER && oldOwner != chessOwner.getValue()) {
                    Platform.runLater(() -> {
                        animateReverse(playerPaint2PR, axis);
                    });
                } else {
                    chessInnerCircle.fillProperty().bind(playerPaint2PR);
                }
                break;

            case PLACEHOLDER:
                chessInnerCircle.fillProperty().unbind();
                chessInnerCircle.setFill(placeHolderColor);
                break;

            case AI:
        }

        if (!chessOwner.getValue().equals(ChessOwner.PLACEHOLDER)) {
            DropShadow dropShadow = new DropShadow();
            dropShadow.setRadius(chessSizePR.doubleValue() + 5);
            dropShadow.setOffsetX(3.0);
            dropShadow.setOffsetY(3.0);
            dropShadow.setColor(Color.rgb(0, 0, 0, 0.45));
            chessInnerCircle.setEffect(dropShadow);
        } else {
            chessInnerCircle.setEffect(null);
        }
    }

    public void animateReverse(ObjectProperty<Paint> newPaint1, Point3D axis) {
        if (axis == null) {
            axis = new Point3D(1, 1, 0);
        }
        RotateTransition rotator1 = createRotator(this, 1, axis);
        RotateTransition rotator2 = createRotator(this, 2, axis);
        ScaleTransition scale1 = createScalar(this, 1);
        rotator1.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                chessInnerCircle.fillProperty().bind(newPaint1);
            }
        });
        rotator2.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                /**
                 * Play chess dropping sound.
                 */
                new Thread(() ->
                {
                    try {
                        AudioClip chessDownSound = new AudioClip(theme.chessDownSoundSourcePR().getValue().toUri().toString());
                        chessDownSound.setVolume(theme.getEffectVolume());
                        chessDownSound.play();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        Log0j.writeInfo("Not able to play down chess sound.");
                    }
                }).start();
            }
        });
        ParallelTransition firstTransition = new ParallelTransition(rotator1, scale1);
        new Thread(() -> {
            /**
             * Play chess up sound.
             */
            try {
                AudioClip chessUpSound = new AudioClip(theme.chessUpSoundSourcePR().getValue().toUri().toString());
                chessUpSound.setVolume(theme.getEffectVolume() * 0.25);
                chessUpSound.play();
            } catch (NullPointerException e) {
                e.printStackTrace();
                Log0j.writeInfo("Not able to play chess up sound.");
            }
        }).start();
        SequentialTransition transition = new SequentialTransition(firstTransition, rotator2);
        Platform.runLater(transition::play);
    }


    private RotateTransition createRotator(Node node, int para, Point3D axis) {
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
