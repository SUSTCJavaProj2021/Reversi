import controller.GameController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import view.*;

import javax.swing.*;
import java.util.Stack;

public class Main extends Application {
    public GridPane mainView;
    public StackPane viewPane;

    public SelectorPage selectorPage;
    public HomePage homePage;
    public PlayPage playPage;

    @Override
    public void start(Stage primaryStage) {

        //Configure pages
        selectorPage = new SelectorPage();
        homePage = new HomePage();
        playPage = new PlayPage();

        mainView = new GridPane();
        //viewPane provides the ability to switch between pages.
        viewPane = new StackPane();
        viewPane.getChildren().add(playPage.root);
        viewPane.getChildren().add(homePage.root);

        GridPane.setHgrow(viewPane, Priority.ALWAYS);
        GridPane.setVgrow(viewPane, Priority.ALWAYS);

        //TEST
        viewPane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        homePage.root.setBackground(new Background(new BackgroundFill(Color.MEDIUMPURPLE, null, null)));
        playPage.root.setBackground(new Background(new BackgroundFill(Color.SEAGREEN, null, null)));

        //END OF TEST

        //Initialize Main View
        mainView.add(selectorPage.root, 0, 0);
        mainView.add(viewPane, 1, 0);
        mainView.setBackground(new Background(new BackgroundFill(Color.web("1C202C"), null, null)));
        {
            ColumnConstraints cs[] = new ColumnConstraints[2];
            for (int i = 0; i < 2; i++) {
                cs[i] = new ColumnConstraints();
                mainView.getColumnConstraints().add(cs[i]);
            }
            cs[0].setMinWidth(50);
            cs[0].setPrefWidth(350);
            cs[0].setMaxWidth(350);
        }

        //Actions
        selectorPage.homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                homePage.root.toFront();
                selectorPage.setCurrentSelection(SelectorPage.Selector.Home);
            }
        });

        selectorPage.playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                playPage.root.toFront();
                selectorPage.setCurrentSelection(SelectorPage.Selector.Play);
            }
        });

        selectorPage.statisticsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                //playPage.root.toFront();
                selectorPage.setCurrentSelection(SelectorPage.Selector.Statistics);
            }
        });

        selectorPage.saveAndLoadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                //playPage.root.toFront();
                selectorPage.setCurrentSelection(SelectorPage.Selector.SaveAndLoad);
            }
        });

        selectorPage.settingsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                //playPage.root.toFront();
                selectorPage.setCurrentSelection(SelectorPage.Selector.Settings);
            }
        });


        // If a game is specified

        playPage.playLocalButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                GameController controller = new GameController(true);
                GamePageLocal gameLocalPage = new GamePageLocal(controller);
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

        Scene homeScene = new Scene(mainView);
        primaryStage.setWidth(1280);
        primaryStage.setHeight(800);
        primaryStage.setScene(homeScene);
        primaryStage.setTitle("Reversi!");
        primaryStage.getIcons().add(iconImg);


        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}