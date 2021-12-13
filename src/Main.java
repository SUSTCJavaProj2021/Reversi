import controller.GameController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import view.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        //Configure pages
        HomePage homePage = new HomePage();
        PlayPage playPage = new PlayPage();
        SelectorPage selectorPage = new SelectorPage();

        GridPane mainView = new GridPane();

        //viewPane provides the ability to switch between pages.
        StackPane viewPane = new StackPane();
        viewPane.getChildren().add(playPage.root);
        viewPane.getChildren().add(homePage.root);
        GridPane.setHgrow(viewPane, Priority.ALWAYS);
        GridPane.setVgrow(viewPane, Priority.ALWAYS);

        viewPane.setBackground(new Background(new BackgroundFill(Color.DEEPSKYBLUE, null, null)));
        homePage.root.setBackground(new Background(new BackgroundFill(Color.MEDIUMPURPLE, null, null)));
        playPage.root.setBackground(new Background(new BackgroundFill(Color.MEDIUMPURPLE, null, null)));

        mainView.add(selectorPage.root, 0, 0);
        mainView.add(viewPane, 1, 0);
        mainView.setBackground(new Background(new BackgroundFill(Color.web("1C202C"), null, null)));
        {
            ColumnConstraints cs[] = new ColumnConstraints[2];
            for (int i = 0; i < 2; i++) {
                cs[i] = new ColumnConstraints();
                mainView.getColumnConstraints().add(cs[i]);
            }
            cs[0].setPercentWidth(25);
        }
        Scene homeScene = new Scene(mainView);

        // If a game is specified
        GameController controller = new GameController(true);
        GamePageLocal gameLocalPage = new GamePageLocal(controller);


        selectorPage.playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                playPage.root.toFront();
            }
        });
        playPage.backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                homePage.root.toFront();
            }
        });

        playPage.playLocalButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                Stage s = new Stage();
                s.setScene(gameLocalPage.scene);
                s.setTitle("LocalPlay");
                s.show();
            }
        });


        //Stage
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        Image iconImg = new Image("/res/icon.png");

        primaryStage.setWidth(1280);
        primaryStage.setHeight(720);
        primaryStage.setScene(homeScene);
        primaryStage.setTitle("Reversi!");
        primaryStage.getIcons().add(iconImg);


        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}