package com.demo.reversi.view.contentpages;

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
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class PlayPage implements Updatable {
    public static final double SELECTOR_WIDTH = 180;

    public final GridPane root;
    public final SelectorPane playSelector;

    // Secondary views
    public final GridPane localPlaySelector;

    public final GridPane onlinePlayPane;


    public final Button newLocalGameButton;
    public final Button loadLocalGameButton;

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

        localPlaySelector = new GridPane();


        //Initialize the Play Selector

        playSelector.addPage("Play Local Game", localPlaySelector);
        playSelector.addPage("Play Online Game", onlinePlayPane);
        playSelector.resetSelectorWidth(120);
        playSelector.init();


        //Buttons need not only to be initialized, but also added to the pane.
        newLocalGameButton = new Button(LiteralConstants.PlayLocalText.toString());
        initPlayLocalGameButton();

        loadLocalGameButton = new Button(LiteralConstants.LoadGameText.toString());
        initLoadGameButton();


        //TEST
        localPlaySelector.add(newLocalGameButton,0,0);
        localPlaySelector.add(loadLocalGameButton, 0, 1);
        //END TEST
    }


    private void initPlayLocalGameButton() {
        //TEST LOCAL GAME
        newLocalGameButton.setPrefHeight(75);
        newLocalGameButton.setPrefWidth(300);

        newLocalGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                GamePageLocal gameLocalPage = new GamePageLocal(gameSystem.startNewGame(), theme);
                Stage gameStage = new Stage();
                gameStage.setScene(new Scene(gameLocalPage.root));
                gameStage.setTitle("Local Game");
                gameStage.getIcons().add(Theme.getAppIcon());

                gameStage.setMinWidth(GamePageLocal.MIN_WIDTH);
                gameStage.setMinHeight(GamePageLocal.MIN_HEIGHT);

                gameStage.show();
                Platform.runLater(theme::registerGame);

                Log0j.writeLog("LocalPlay (New Game) initialized.");

                gameStage.setOnCloseRequest(ActionEvent->{
                    Platform.runLater(theme::unregisterGame);
                });
            }
        });
        GridPane.setHalignment(newLocalGameButton, HPos.CENTER);
    }

    private void initLoadGameButton() {
        //TEST LOAD GAME
        loadLocalGameButton.setPrefHeight(75);
        loadLocalGameButton.setPrefWidth(300);

        loadLocalGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                //todo: change default index property
                SimpleIntegerProperty indexProperty = new SimpleIntegerProperty(2);

                if (indexProperty.intValue() != -1) {
                    GamePageLocal gameLocalPage = new GamePageLocal(gameSystem.loadGame(indexProperty.intValue(), false), theme);
                    Stage gameStage = new Stage();
                    gameStage.setScene(new Scene(gameLocalPage.root));
                    gameStage.setTitle("Local Game");
                    gameStage.getIcons().add(Theme.getAppIcon());
                    gameStage.setMinWidth(GamePageLocal.MIN_WIDTH);
                    gameStage.setMinHeight(GamePageLocal.MIN_HEIGHT);
                    gameStage.show();
                    Log0j.writeLog("LocalPlay (Load Game) initialized.");
                }
            }
        });
        GridPane.setHalignment(loadLocalGameButton, HPos.CENTER);
    }

    @Override
    public void update() {

    }
}
