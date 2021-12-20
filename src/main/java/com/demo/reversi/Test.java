package com.demo.reversi;

import com.demo.reversi.component.panes.InfoPane;
import com.demo.reversi.controller.SimplePlayer;
import com.demo.reversi.themes.Theme;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class Test extends Application{

    @Override
    public void start(Stage primaryStage){
        ObjectProperty<Paint> wdnmd = new SimpleObjectProperty<>(Color.BLACK);
        primaryStage.setScene(new Scene(new InfoPane(new SimplePlayer("WNDMD"), new Theme(primaryStage), wdnmd)));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}