package com.demo.reversi.view.contentpages;

import com.demo.reversi.component.MetroButton;
import com.demo.reversi.component.TitleLabel;
import com.demo.reversi.component.selector.SelectorPane;
import com.demo.reversi.controller.local.SimpleGameSystem;
import com.demo.reversi.res.lang.LiteralConstants;
import com.demo.reversi.logger.Log0j;
import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.Updatable;
import com.demo.reversi.view.gamepages.GamePageLocal;
import com.demo.reversi.view.gamepages.GamePreviewPane;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class PlayPage implements Updatable {
    public static final double SELECTOR_WIDTH = 180;

    public final GridPane root;
    public final SelectorPane playSelector;

    // Secondary views
    public final GridPane localPlayPane;
    public final GridPane LANPlayPane;
    public final GridPane onlinePlayPane;

    public final GamePreviewPane newGamePreview;    //For creating a new game.
    public final GamePreviewPane loadGamePreview;   //For loading game from file.

    public final SimpleGameSystem gameSystem;
    public final Theme theme;


    public PlayPage(SimpleGameSystem gameSystem, Theme theme) {
        this.gameSystem = gameSystem;
        this.theme = theme;

        /*
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
        root.add(new TitleLabel(LiteralConstants.PlayPageTitle.toString(), theme), 0, 0, 2, 1);
        playSelector = new SelectorPane(theme);
        root.add(playSelector, 0, 1);


        //Initialize all secondary panes.

        localPlayPane = new GridPane();
        localPlayPane.add(new TitleLabel("Play Game on Local Machine", theme), 0, 0);

        LANPlayPane = new GridPane();
        LANPlayPane.add(new TitleLabel("Play Game in Local Area Network", theme), 0, 0);
        LANPlayPane.add(new Label("Actually, there is no LAN game yet.\n QAQ"), 0, 1);

        onlinePlayPane = new GridPane();
        onlinePlayPane.add(new TitleLabel("Play Online Game", theme), 0, 0);
        onlinePlayPane.add(new Label("Actually, there is no online game yet.\n QAQ"), 0, 1);


        //Initialize the Play Selector

        playSelector.addPage("Local", localPlayPane);
        playSelector.addPage("LAN", LANPlayPane);
        playSelector.addPage("Online", onlinePlayPane);
        playSelector.resetSelectorWidth(120);
        playSelector.init();

        //Loading default selections
        newGamePreview = new GamePreviewPane(gameSystem, theme, GamePreviewPane.PreviewType.NEW_GAME);
        localPlayPane.add(newGamePreview, 0, 1);

        loadGamePreview = new GamePreviewPane(gameSystem,theme,GamePreviewPane.PreviewType.LOAD_GAME_FROM_FILE);
        localPlayPane.add(loadGamePreview,1,1);

        initRelations();
        //TEST
        //END TEST
    }

    /**
     * Switch the bgm!
     */
    private void initRelations() {
        root.visibleProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue) {
                theme.registerPlayPageBGM();
            } else {
                theme.unregisterPlayPageBGM();
            }
        }));
    }


    private void initLoadGamePreview(){

    }

    @Override
    public void update() {

    }
}
