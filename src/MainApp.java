import controller.GameSystem;
import controller.logger.Log0j;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import view.MainView;
import view.Theme;

import java.net.URISyntaxException;

public class MainApp extends Application {
    public static final int MAIN_WINDOW_PREF_WIDTH = 1280;
    public static final int MAIN_WINDOW_PREF_HEIGHT = 800;

    public Stage primaryStage;
    public MainView mainView;
    public GameSystem gameSystem;
    public Theme theme;

    @Override
    public void start(Stage primaryStage) {
        //Initialize Game System
        this.primaryStage = primaryStage;
        gameSystem = new GameSystem();

        //Initialize MainView

        //todo: Solve Theme loading issue.
        theme = new Theme(primaryStage);
        mainView = new MainView(gameSystem, theme);

        //Stage
        Image iconImg = new Image("/res/icons/App.png");

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