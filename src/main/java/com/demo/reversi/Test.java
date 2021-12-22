package com.demo.reversi;

import com.demo.reversi.component.panes.InfoPane;
import com.demo.reversi.controller.local.SimplePlayer;
import com.demo.reversi.themes.Theme;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Test extends Application{

    @Override
    public void start(Stage primaryStage){
        ObjectProperty<Color> wdnmd = new SimpleObjectProperty<>(Color.BLACK);
        GridPane gridPane = new GridPane();
        gridPane.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
        gridPane.add(new InfoPane(new SimplePlayer("WNDMD"), new Theme(primaryStage), wdnmd),0,0);
        primaryStage.setScene(new Scene(gridPane));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}