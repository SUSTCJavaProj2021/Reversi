package com.demo.reversi;

import com.demo.reversi.controller.basic.GameSystem;
import com.demo.reversi.controller.basic.game.Game;
import com.demo.reversi.controller.interfaces.GameSystemLayer;
import com.demo.reversi.controller.local.SimpleGameSystem;
import com.demo.reversi.controller.show.GameSystemController;
import com.demo.reversi.logger.Log0j;
import com.demo.reversi.view.MainView;
import com.demo.reversi.themes.Theme;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URISyntaxException;

public class MainApp extends Application {
    public static final int MAIN_WINDOW_PREF_WIDTH = 1280;
    public static final int MAIN_WINDOW_PREF_HEIGHT = 800;

    public Stage primaryStage;
    public MainView mainView;
    public GameSystemLayer gameSystem;
    public Theme theme;

    @Override
    public void start(Stage primaryStage) throws URISyntaxException {
        //Initialize Game System
        this.primaryStage = primaryStage;

        //Loading...

        gameSystem = new GameSystemController();


        //Initialize MainView

        theme = new Theme(primaryStage);
        theme.loadTheme();
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
        Log0j.writeInfo("Stage ready.");

        primaryStage.show();
        Log0j.writeInfo("Stage shown.");

//        Log0j.writeInfo(mainView.playPage.loadGamePreview.player1Info.widthProperty().getValue().toString());
//        primaryStage.setFullScreen(true);
//        primaryStage.setFullScreenExitHint("Press Esc to exit fullscreen.");
//        primaryStage.setFullScreenExitKeyCombination(KeyCombination.valueOf("Esc"));

    }



    public static void main(String[] args) {
        launch(args);
    }

    public void setCloseAction() {
        primaryStage.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                curtainCall();
            }
        });
        Log0j.writeInfo("primaryStage CloseAction set.");
    }

    public void curtainCall(){
        gameSystem.save();
        theme.saveTheme();
    }

}