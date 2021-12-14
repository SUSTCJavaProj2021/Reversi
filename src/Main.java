import controller.GameController;
import controller.Log0j;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import view.*;

public class Main extends Application {
    public GridPane mainView;
    public BorderPane viewCoverPane;
    public StackPane viewPane;

    public SelectorPage selectorPage;
    public HomePage homePage;
    public PlayPage playPage;
    public StatisticsPage statisticsPage;
    public SaveAndLoadPage saveAndLoadPage;
    public SettingsPage settingsPage;
    public AboutPage aboutPage;

    @Override
    public void start(Stage primaryStage) {
        //Initialize Controller
        GameController controller = new GameController(true);
        

        //Configure pages
        selectorPage = new SelectorPage();
        homePage = new HomePage(controller);
        playPage = new PlayPage(controller);
        statisticsPage = new StatisticsPage(controller);
        saveAndLoadPage = new SaveAndLoadPage(controller);
        settingsPage = new SettingsPage(controller);
        aboutPage = new AboutPage();
        Log0j.writeLog(getClass().toString(), "All pages loaded.");

        //viewPane provides the ability to switch between pages.
        viewPane = new StackPane();
        viewPane.getChildren().addAll(aboutPage.root, settingsPage.root, saveAndLoadPage.root, statisticsPage.root, playPage.root, homePage.root);


        GridPane.setHgrow(viewPane, Priority.ALWAYS);
        GridPane.setVgrow(viewPane, Priority.ALWAYS);

        viewPane.setBackground(new Background(new BackgroundFill(Color.web("1D1F2C"), null, null)));
        Log0j.writeLog(getClass().toString(), "View Pane loaded.");

        //Cover
        viewCoverPane = new BorderPane();
        {
            HBox hBox1 = new HBox();
            HBox hBox2 = new HBox();
            hBox1.setMinHeight(50);
            hBox1.setMaxHeight(50);
            hBox1.setMinWidth(HBox.USE_COMPUTED_SIZE);
            hBox2.setMinHeight(50);
            hBox2.setMaxHeight(50);
            hBox2.setMinWidth(HBox.USE_COMPUTED_SIZE);
            viewCoverPane.setTop(hBox1);
            viewCoverPane.setBottom(hBox2);
            VBox vBox1 = new VBox();
            VBox vBox2 = new VBox();
            vBox1.setMinWidth(50);
            vBox1.setMaxWidth(50);
            vBox1.setMinHeight(HBox.USE_COMPUTED_SIZE);
            vBox2.setMinWidth(50);
            vBox2.setMaxWidth(50);
            vBox2.setMinHeight(HBox.USE_COMPUTED_SIZE);
            viewCoverPane.setLeft(vBox1);
            viewCoverPane.setRight(vBox2);
        }
        viewCoverPane.setCenter(viewPane);
        viewCoverPane.setBackground(new Background(new BackgroundFill(Color.web("26272F"), new CornerRadii(15), null)));

        //Initialize Main View
        mainView = new GridPane();
        {
            Separator s = new Separator(Orientation.VERTICAL);
            s.setOpacity(0);
            mainView.add(s, 0, 0);
        }
        mainView.add(selectorPage.root, 1, 0);
        {
            Separator s = new Separator(Orientation.VERTICAL);
            s.setOpacity(0);
            mainView.add(s, 2, 0);
        }
        mainView.add(viewCoverPane, 3, 0);


        mainView.setBackground(new Background(new BackgroundFill(Color.web("1C202C"), null, null)));

        {
            ColumnConstraints cs[] = new ColumnConstraints[2];
            for (int i = 0; i < 2; i++) {
                cs[i] = new ColumnConstraints();
                mainView.getColumnConstraints().add(cs[i]);
            }
            cs[1].setMinWidth(150);
            cs[1].setPrefWidth(250);
            cs[1].setMaxWidth(250);
        }

        Log0j.writeLog(getClass().toString(), "Main View initialized.");


        Log0j.writeLog(getClass().toString(), "Initializing Selector actions.");

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
                statisticsPage.root.toFront();
                selectorPage.setCurrentSelection(SelectorPage.Selector.Statistics);
            }
        });

        selectorPage.saveAndLoadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                saveAndLoadPage.root.toFront();
                selectorPage.setCurrentSelection(SelectorPage.Selector.SaveAndLoad);
            }
        });

        selectorPage.settingsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                settingsPage.root.toFront();
                selectorPage.setCurrentSelection(SelectorPage.Selector.Settings);
            }
        });

        selectorPage.aboutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                aboutPage.root.toFront();
                selectorPage.setCurrentSelection(SelectorPage.Selector.About);
            }
        });
        Log0j.writeLog(getClass().toString(), "Selector Actions initialized.");

        // If a game is specified

        playPage.playLocalButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                GamePageLocal gameLocalPage = new GamePageLocal(controller);
                Stage s = new Stage();
                s.setScene(gameLocalPage.rootScene);
                s.setTitle("LocalPlay");
                s.show();
            }
        });
        Log0j.writeLog(getClass().toString(), "LocalPlay (New Game) initialized.");

        //Stage
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        Image iconImg = new Image("/res/icon.png");

        Scene mainScene = new Scene(mainView);
        primaryStage.setWidth(1280);
        primaryStage.setHeight(800);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Reversi!");
        primaryStage.getIcons().add(iconImg);
//        mainScene.getStylesheets().add("res/stylesheet.css");
        Log0j.writeLog(getClass().toString(), "Stage ready.");

        primaryStage.show();
        Log0j.writeLog(getClass().toString(), "Stage shown.");
    }

    public static void main(String[] args) {
        launch(args);
    }

}