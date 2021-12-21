package com.demo.reversi.view.contentpages;

import com.demo.reversi.component.MetroButton;
import com.demo.reversi.component.TitleLabel;
import com.demo.reversi.component.selector.SelectorPane;
import com.demo.reversi.controller.SimpleGameSystem;
import com.demo.reversi.res.lang.LiteralConstants;
import com.demo.reversi.logger.Log0j;
import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.Updatable;
import com.demo.reversi.view.gamepages.GamePageLocal;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class PlayPage implements Updatable {
    public static final double SELECTOR_WIDTH = 180;

    public final GridPane root;
    public final SelectorPane playSelector;

    // Secondary views
    public final GridPane localPlaySelector;

    public final GridPane onlinePlayPane;


    public final MetroButton newLocalGameButton;
    public final MetroButton loadLocalGameButton;

    public final SimpleGameSystem gameSystem;
    public final Theme theme;


    public PlayPage(SimpleGameSystem gameSystem, Theme theme) {
        this.gameSystem = gameSystem;
        this.theme = theme;

        /**
         * Overall Root Structure:
         *
         * ----------------------
         * Title (2 column spans)
         * ---------------------
         *          |
         * Selector | viewStack
         *          |
         * ---------------------
         */
        root = new GridPane();
        root.add(new TitleLabel(LiteralConstants.PlayPageTitle.toString(), theme), 0, 0);
        GridPane.setColumnSpan(root.getChildren().get(0), 2);
        playSelector = new SelectorPane(theme);
        root.add(playSelector, 0, 1);


        //Initialize all secondary panes.


        onlinePlayPane = new GridPane();
        onlinePlayPane.add(new TitleLabel("Play Online Game", theme), 0, 0);
        onlinePlayPane.add(new Label("Actually, there is no online game yet.\n QAQ"), 0, 1);

        localPlaySelector = new GridPane();


        //Initialize the Play Selector

        playSelector.addPage("Local", localPlaySelector);
        playSelector.addPage("Online", onlinePlayPane);
        playSelector.resetSelectorWidth(120);
        playSelector.init();


        //Buttons need not only to be initialized, but also added to the pane.
        newLocalGameButton = new MetroButton(LiteralConstants.PlayLocalText.toString(), theme);
        initPlayLocalGameButton();

        loadLocalGameButton = new MetroButton(LiteralConstants.LoadGameText.toString(), theme);
        initLoadGameButton();


        //TEST
        localPlaySelector.add(newLocalGameButton, 0, 0);
        localPlaySelector.add(loadLocalGameButton, 0, 1);
        //END TEST
    }


    private void initPlayLocalGameButton() {
        //TEST LOCAL GAME

        newLocalGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                GamePageLocal gameLocalPage = new GamePageLocal(gameSystem, -1, theme);
                initGame(gameLocalPage);
            }
        });
        GridPane.setHalignment(newLocalGameButton, HPos.CENTER);
    }

    private void initLoadGameButton() {
        //TEST LOAD GAME

        loadLocalGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                //todo: change default index property
                SimpleIntegerProperty indexProperty = new SimpleIntegerProperty(2);

                if (indexProperty.intValue() != -1) {
                    //todo: change loading behavior
                    GamePageLocal gameLocalPage = new GamePageLocal(gameSystem, -1, theme);
                    initGame(gameLocalPage);
                }
            }
        });
        GridPane.setHalignment(loadLocalGameButton, HPos.CENTER);
    }

    private void initGame(GamePageLocal gameLocalPage) {
        Stage gameStage = new Stage();
        gameStage.setScene(new Scene(gameLocalPage.root));
        gameStage.setTitle("Local Game");
        gameStage.getIcons().add(Theme.getAppIcon());

        gameStage.setMinWidth(GamePageLocal.MIN_WIDTH);
        gameStage.setMinHeight(GamePageLocal.MIN_HEIGHT);

        gameStage.show();
        Platform.runLater(theme::registerGame);
        Log0j.writeInfo("LocalPlay (Load Game) initialized.");
        gameStage.setOnCloseRequest(ActionEvent -> {
            Platform.runLater(theme::unregisterGame);
        });
    }

    @Override
    public void update() {

    }
}
