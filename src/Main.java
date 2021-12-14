import controller.GameSystem;
import controller.Log0j;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.MainView;

public class Main extends Application {
    public MainView mainView;

    @Override
    public void start(Stage primaryStage) {
        //Initialize Game System
        GameSystem gameSystem = new GameSystem();

        //Initialize MainView
        mainView = new MainView(gameSystem);


        //Stage
        Image iconImg = new Image("/res/icons/App.png");

        Scene mainScene = new Scene(mainView);

        primaryStage.setWidth(1280);
        primaryStage.setHeight(800);

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