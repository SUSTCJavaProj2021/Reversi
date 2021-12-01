import component.playpage.PlayLocalButton;
import controller.GameController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;
import view.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        //Configure pages
        HomePage homePage = new HomePage(1280, 800, "/res/default_background.jpg");
        PlayPage playPage = new PlayPage(1280, 800);

        // If a game is specified
        GameController controller = new GameController(true);
        GamePageLocal gameLocalPage = new GamePageLocal(controller);


        homePage.playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                primaryStage.setScene(playPage.scene);
            }
        });

        playPage.playLocalButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                primaryStage.setScene(gameLocalPage.scene);
            }
        });


        //Stage
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        Image iconImg = new Image("/res/icon.png");
        primaryStage.setScene(homePage.scene);
        primaryStage.setTitle("Reversi!");
        primaryStage.getIcons().add(iconImg);


        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}