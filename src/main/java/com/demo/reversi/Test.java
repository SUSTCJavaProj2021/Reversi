package com.demo.reversi;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Test extends Application{

    @Override
    public void start(Stage primaryStage){
        primaryStage.setScene(new Scene(new TestComponent()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}