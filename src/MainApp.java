import controller.GameSystem;
import controller.logger.Log0j;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import view.MainView;
import view.Theme;

import java.net.URISyntaxException;

public class MainApp extends Application {
    public MainView mainView;

    @Override
    public void start(Stage primaryStage) {
        //Initialize Game System
        GameSystem gameSystem = new GameSystem();

        //Initialize MainView

        //todo: Solve Theme loading issue.
        Theme theme = new Theme();
        mainView = new MainView(gameSystem, theme);


        //Stage
        Image iconImg = new Image("/res/icons/App.png");

        Scene mainScene = new Scene(mainView);

        primaryStage.setWidth(1280);
        primaryStage.setHeight(800);

        //Experience-based numbers
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(450);

        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Reversi!");
        primaryStage.getIcons().add(iconImg);
        primaryStage.setOnCloseRequest(windowEvent -> Platform.exit());

        Log0j.writeLog("Stage ready.");

        primaryStage.show();
        Log0j.writeLog("Stage shown.");

    }

    public static void main(String[] args) {
        launch(args);
    }

}