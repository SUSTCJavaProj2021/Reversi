package view;

import component.selector.MainSelectorPane;
import controller.SimpleGameSystem;
import controller.logger.Log0j;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
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
    public static final double SELECTOR_WIDTH = 180;

    public final MainSelectorPane mainSelectorPane;

    public HomePage homePage;
    public PlayPage playPage;
    public StatisticsPage statisticsPage;
    public SaveAndLoadPage saveAndLoadPage;
    public SettingsPage settingsPage;
    public AboutPage aboutPage;

    public SimpleGameSystem gameSystem;
    public Theme theme;


    public MainView(SimpleGameSystem gameSystem, Theme theme) {
        super();
        this.gameSystem = gameSystem;
        this.theme = theme;

        homePage = new HomePage(gameSystem, theme);
        playPage = new PlayPage(gameSystem, theme);
        statisticsPage = new StatisticsPage(gameSystem, theme);
        saveAndLoadPage = new SaveAndLoadPage(gameSystem, theme);
        settingsPage = new SettingsPage(gameSystem, theme);
        aboutPage = new AboutPage(theme);
        Log0j.writeLog("Content pages loaded.");


        //todo: switch to SelectorPane
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

        mainSelectorPane = new MainSelectorPane(selectorTitle, theme);
        mainSelectorPane.addPage(LiteralConstants.HomePageTitle.toString(), homePage.root, new Icon("/res/icons/Home.png"));
        mainSelectorPane.addPage(LiteralConstants.PlayPageTitle.toString(), playPage.root, new Icon("/res/icons/Play.png"));
        mainSelectorPane.addPage(LiteralConstants.StatisticsPageTitle.toString(), statisticsPage.root, new Icon("/res/icons/Statistics.png"));
        mainSelectorPane.addPage(LiteralConstants.SaveAndLoadPageTitle.toString(), saveAndLoadPage.root, new Icon("/res/icons/SaveAndLoad.png"));
        mainSelectorPane.addPage(LiteralConstants.SettingsPageTitle.toString(), settingsPage.root, new Icon("/res/icons/Settings.png"));
        mainSelectorPane.addPage(LiteralConstants.AboutPageTitle.toString(), aboutPage.root, new Icon("/res/icons/About.png"));
        mainSelectorPane.init();

        //Initialize Content Selector

        Log0j.writeLog("Selector Actions initialized.");


        //Initialize Main View


        theme.bindToBackPane(backgroundProperty());
        add(mainSelectorPane, 0, 0);

        Log0j.writeLog("Main View initialized.");

        add(new MediaView(theme.getBgmPlayer()), 0, 0);
        theme.getBgmPlayer().play();
    }
}
