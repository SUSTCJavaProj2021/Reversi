package com.demo.reversi;

import com.demo.reversi.controller.SimpleGameSystem;
import com.demo.reversi.logger.Log0j;
import com.demo.reversi.view.MainView;
import com.demo.reversi.themes.Theme;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URISyntaxException;

public class MainApp extends Application {
    public static final int MAIN_WINDOW_PREF_WIDTH = 1280;
    public static final int MAIN_WINDOW_PREF_HEIGHT = 800;

    public Stage primaryStage;
    public MainView mainView;
    public SimpleGameSystem gameSystem;
    public Theme theme;

    @Override
    public void start(Stage primaryStage) throws URISyntaxException {
        //Initialize Game System
        this.primaryStage = primaryStage;
        gameSystem = new SimpleGameSystem();

        //Initialize MainView

        //todo: Solve Theme loading issue.
        theme = new Theme(primaryStage);
        mainView = new MainView(gameSystem, theme);

        //Stage
        Image iconImg = Theme.getAppIcon();

        Scene mainScene = new Scene(mainView);


        //Experience-based numbers
        primaryStage.setMinWidth(Theme.DEFAULT_MAIN_WINDOW_MIN_WIDTH);
        primaryStage.setMinHeight(Theme.DEFAULT_MAIN_WINDOW_MIN_HEIGHT);

        //Setting preferred size for primaryStage
        primaryStage.setWidth(MAIN_WINDOW_PREF_WIDTH);
        primaryStage.setHeight(MAIN_WINDOW_PREF_HEIGHT);

        //This method has to be called after the primaryStage
        //has been initialized with last saved preference.
        theme.bindToStage(primaryStage);


        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Reversi!");
        primaryStage.getIcons().add(iconImg);


        setCloseAction();
        Log0j.writeLog("Stage ready.");

        primaryStage.show();
        Log0j.writeLog("Stage shown.");

    }

    public static void main(String[] args) {
        launch(args);
    }

    public void setCloseAction(){
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                gameSystem.save();
                theme.saveTheme();
            }
        });
        Log0j.writeLog("primaryStage CloseAction set.");
    }

}