package com.demo.reversi;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import javafx.scene.shape.CubicCurve;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author ik
 */
public class RainDrop extends Application {

    @Override
    public void start(Stage primaryStage) {
        int i;
        Group root = new Group();

        for (i = 0; i < 3200; i++) {
            CubicCurve cubicCurve = new CubicCurve();
            drop(i, cubicCurve);
            System.out.println("Abstract rain");
            root.getChildren().add(cubicCurve);
        }

        Scene scene = new Scene(root, 320, 568);

        Timeline timeline = new Timeline();

        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO, // set start position at 0
                        new KeyValue(root.translateXProperty(), -700),
                        new KeyValue(root.translateYProperty(), -3200)
                ),
                new KeyFrame(new Duration(30000), // set end position at 40s
                        new KeyValue(root.translateXProperty(), 0),
                        new KeyValue(root.translateYProperty(), 600)
                )
        );

// play 40s of animation
        timeline.play();

        primaryStage.setTitle("Abstract rain");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void drop(int i, CubicCurve cubicCurve) {
        //Drawing a cubic curve

        int y = (i / 16) * 80;
        //Setting properties to cubic curve
        int x = 80 * (i - 16 * (int) (i / 16));
        System.out.println(x);
        cubicCurve.setStartX(0);
        cubicCurve.setStartY(0);
        cubicCurve.setControlX1(40);
        cubicCurve.setControlY1(140);
        cubicCurve.setControlX2(140);
        cubicCurve.setControlY2(140);
        cubicCurve.setEndX(0);
        cubicCurve.setEndY(0);
        cubicCurve.setTranslateX(x);
        cubicCurve.setTranslateY(y);
        cubicCurve.setStroke(Color.GREEN);
        cubicCurve.setFill(Color.TRANSPARENT);
    }

}