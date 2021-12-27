package com.demo.reversi;

import com.demo.reversi.themes.Theme;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.net.URISyntaxException;

public class Test extends Application {
    @Override
    public void start(Stage primaryStage) {
        GridPane root = new GridPane();
        root.setMinWidth(800);
        root.setMinHeight(800);
        try {
            File file = new File(Theme.class.getResource("Background.jpg").toURI().toString());
            root.setBackground(new Background(new BackgroundImage(new Image(file.getPath()),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}