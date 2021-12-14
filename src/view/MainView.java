package view;

import component.selector.SelectorPage;
import controller.GameSystem;
import controller.Log0j;
import res.literal.LiteralConstants;
import view.contentpages.*;
import res.icons.Icon;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;


public class MainView extends GridPane {
    public static final double VIEWCOVER_CORNER_RADII = 10;
    public static final double VIEWCOVER_HORIZONTAL_DIST = 20;
    public static final double VIEWCOVER_VERTICAL_DIST = 50;

    public BorderPane viewCoverPane;
    public StackPane viewPane;

    public SelectorPage selectorPage;
    public HomePage homePage;
    public PlayPage playPage;
    public StatisticsPage statisticsPage;
    public SaveAndLoadPage saveAndLoadPage;
    public SettingsPage settingsPage;
    public AboutPage aboutPage;

    public MainView(GameSystem gameSystem) {

        super();


        homePage = new HomePage(gameSystem);
        playPage = new PlayPage(gameSystem);
        statisticsPage = new StatisticsPage(gameSystem);
        saveAndLoadPage = new SaveAndLoadPage(gameSystem);
        settingsPage = new SettingsPage(gameSystem);
        aboutPage = new AboutPage();
        Log0j.writeLog("Content pages loaded.");

        homePage.root.setVisible(false);
        playPage.root.setVisible(false);
        statisticsPage.root.setVisible(false);
        saveAndLoadPage.root.setVisible(false);
        settingsPage.root.setVisible(false);
        aboutPage.root.setVisible(false);


        //viewPane provides the ability to switch between pages.
        viewPane = new StackPane();
        viewPane.getChildren().addAll(aboutPage.root, settingsPage.root, saveAndLoadPage.root, statisticsPage.root, playPage.root, homePage.root);

        GridPane.setHgrow(viewPane, Priority.ALWAYS);
        GridPane.setVgrow(viewPane, Priority.ALWAYS);
        Log0j.writeLog("View Pane loaded.");


        //Cover
        viewCoverPane = new BorderPane();
        {
            HBox hBox1 = new HBox();
            HBox hBox2 = new HBox();
            hBox1.setMinHeight(VIEWCOVER_HORIZONTAL_DIST);
            hBox1.setMaxHeight(VIEWCOVER_HORIZONTAL_DIST);
            hBox1.setMinWidth(HBox.USE_COMPUTED_SIZE);
            hBox2.setMinHeight(VIEWCOVER_HORIZONTAL_DIST);
            hBox2.setMaxHeight(VIEWCOVER_HORIZONTAL_DIST);
            hBox2.setMinWidth(HBox.USE_COMPUTED_SIZE);
            viewCoverPane.setTop(hBox1);
            viewCoverPane.setBottom(hBox2);
            VBox vBox1 = new VBox();
            VBox vBox2 = new VBox();
            vBox1.setMinWidth(VIEWCOVER_VERTICAL_DIST);
            vBox1.setMaxWidth(VIEWCOVER_VERTICAL_DIST);
            vBox1.setMinHeight(HBox.USE_COMPUTED_SIZE);
            vBox2.setMinWidth(VIEWCOVER_VERTICAL_DIST);
            vBox2.setMaxWidth(VIEWCOVER_VERTICAL_DIST);
            vBox2.setMinHeight(HBox.USE_COMPUTED_SIZE);
            viewCoverPane.setLeft(vBox1);
            viewCoverPane.setRight(vBox2);
        }
        viewCoverPane.setCenter(viewPane);
        GridPane.setHgrow(viewCoverPane, Priority.ALWAYS);
        GridPane.setVgrow(viewCoverPane, Priority.ALWAYS);
        viewCoverPane.setBackground(new Background(new BackgroundFill(Color.web("26272F"), new CornerRadii(VIEWCOVER_CORNER_RADII), null)));
        Log0j.writeLog("ViewCover Pane loaded.");


        //Initialize Main Icon
        Image img = new Image("/res/icons/App.png");
        ImageView imageView = new ImageView(img);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(40);
        Label selectorTitle = new Label();
        selectorTitle.setGraphic(imageView);
        selectorTitle.setTextFill(Color.WHITE);
        selectorTitle.setText("Reversi!");
        selectorTitle.setTextAlignment(TextAlignment.CENTER);
        selectorTitle.setFont(new Font("Segoe UI", 20));
        selectorPage = new SelectorPage(selectorTitle);


        //Initialize Content Selector


        selectorPage.addSelection(LiteralConstants.HomeButtonText.toString(), homePage.root, new Icon("/res/icons/Home.png"));
        selectorPage.addSelection(LiteralConstants.PlayButtonText.toString(), playPage.root, new Icon("/res/icons/Play.png"));
        selectorPage.addSelection(LiteralConstants.StatisticsButtonText.toString(), statisticsPage.root, new Icon("/res/icons/Statistics.png"));
        selectorPage.addSelection(LiteralConstants.SaveAndLoadButtonText.toString(), saveAndLoadPage.root, new Icon("/res/icons/SaveAndLoad.png"));
        selectorPage.addSelection(LiteralConstants.SettingsButtonText.toString(), settingsPage.root, new Icon("/res/icons/Settings.png"));
        selectorPage.addSelection(LiteralConstants.AboutButtonText.toString(), aboutPage.root, new Icon("/res/icons/About.png"));
        selectorPage.setExitButton(new Icon("/res/icons/Exit.png"));
        selectorPage.init();
        Log0j.writeLog("Selector Actions initialized.");


        //Initialize Main View
        add(selectorPage.root, 0, 0);
        add(viewCoverPane, 1, 0);

        setBackground(new Background(new BackgroundFill(Color.web("1C202C"), null, null)));

        {
            ColumnConstraints cs = new ColumnConstraints();
            cs.setMinWidth(180);
            cs.setPrefWidth(180);
            cs.setMaxWidth(180);
            getColumnConstraints().add(cs);
        }

        Log0j.writeLog("Main View initialized.");
    }
}
