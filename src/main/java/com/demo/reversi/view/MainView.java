package com.demo.reversi.view;

import com.demo.reversi.component.selector.MainSelectorPane;
import com.demo.reversi.controller.local.SimpleGameSystem;
import com.demo.reversi.logger.Log0j;
import com.demo.reversi.res.icons.Icon;
import com.demo.reversi.res.lang.LiteralConstants;
import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.contentpages.*;
import javafx.scene.media.MediaView;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.net.URISyntaxException;


public class MainView extends GridPane {
    public static final double VIEWCOVER_CORNER_RADII = 10;
    public static final double VIEWCOVER_HORIZONTAL_DIST = 20;
    public static final double VIEWCOVER_VERTICAL_DIST = 50;
    public static final double SELECTOR_WIDTH = 180;

    public final MainSelectorPane mainSelectorPane;

    public HomePage homePage;
    public PlayPage playPage;
    public StatisticsPage statisticsPage;
    public GameSystemPage gameSystemPage;
    public SettingsPage settingsPage;
    public AboutPage aboutPage;

    public SimpleGameSystem gameSystem;
    public Theme theme;


    public MainView(SimpleGameSystem gameSystem, Theme theme) throws URISyntaxException {
        super();
        this.gameSystem = gameSystem;
        this.theme = theme;

        homePage = new HomePage(gameSystem, theme);
        playPage = new PlayPage(gameSystem, theme);
        statisticsPage = new StatisticsPage(gameSystem, theme);
        gameSystemPage = new GameSystemPage(gameSystem, theme);
        settingsPage = new SettingsPage(gameSystem, theme);
        aboutPage = new AboutPage(theme);
        Log0j.writeInfo("Content pages loaded.");


        //todo: switch to SelectorPane
        //Initialize Main Icon
        Image img = new Image(Theme.appIcon.getUrl());
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
        mainSelectorPane.addPage(LiteralConstants.HomePageTitle.toString(), homePage.root, new Icon(theme.getClass().getResource("icons/Home.png").toURI().toString()));
        mainSelectorPane.addPage(LiteralConstants.PlayPageTitle.toString(), playPage.root, new Icon(theme.getClass().getResource("icons/Play.png").toURI().toString()));
        mainSelectorPane.addPage(LiteralConstants.StatisticsPageTitle.toString(), statisticsPage.root, new Icon(theme.getClass().getResource("icons/Statistics.png").toURI().toString()));
        mainSelectorPane.addPage(LiteralConstants.GameSystemPageTitle.toString(), gameSystemPage.root, new Icon(theme.getClass().getResource("icons/SaveAndLoad.png").toURI().toString()));
        mainSelectorPane.addPage(LiteralConstants.SettingsPageTitle.toString(), settingsPage.root, new Icon(theme.getClass().getResource("icons/Settings.png").toURI().toString()));
        mainSelectorPane.addPage(LiteralConstants.AboutPageTitle.toString(), aboutPage.root, new Icon(theme.getClass().getResource("icons/About.png").toURI().toString()));
        mainSelectorPane.init();

        //Initialize Content Selector

        Log0j.writeInfo("Selector Actions initialized.");


        //Initialize Main View

        backgroundProperty().bind(theme.backPanePR());
        add(mainSelectorPane, 0, 0);

        Log0j.writeInfo("Main View initialized.");
        //todo: ?
        if (theme.getBGMPlayer() != null) {
            add(new MediaView(theme.getBGMPlayer()), 0, 0);
            theme.getBGMPlayer().play();
            Log0j.writeInfo("BGM loaded to Main View. Attention: Controls are still embedded in class Theme.");
        }
        else{
            Log0j.writeInfo("Background BGM isn't loaded because the pointer to BGMPlayer is null.");
        }

    }
}
