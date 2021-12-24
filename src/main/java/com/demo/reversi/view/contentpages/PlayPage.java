package com.demo.reversi.view.contentpages;

import com.demo.reversi.component.TitleLabel;
import com.demo.reversi.component.panes.SmoothishScrollPane;
import com.demo.reversi.component.selector.SelectorPane;
import com.demo.reversi.controller.local.SimpleGameSystem;
import com.demo.reversi.res.lang.LiteralConstants;
import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.Updatable;
import com.demo.reversi.view.gamepages.GamePreviewPane;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class PlayPage implements Updatable {
    public static final double SELECTOR_WIDTH = 180;

    public final GridPane root;
    public final SelectorPane playSelector;

    // Secondary views
    public final GridPane localPlayContainer;
    public final GridPane LANPlayContainer;
    public final GridPane onlinePlayContainer;

    public final FlowPane localPlayPane;

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

        localPlayContainer = new GridPane();
        localPlayContainer.add(new TitleLabel("Play Game on Local Machine", theme), 0, 0);

        LANPlayContainer = new GridPane();
        LANPlayContainer.add(new TitleLabel("Play Game in Local Area Network", theme), 0, 0);
        LANPlayContainer.add(new Label("Actually, there is no LAN game yet.\n QAQ"), 0, 1);

        onlinePlayContainer = new GridPane();
        onlinePlayContainer.add(new TitleLabel("Play Online Game", theme), 0, 0);
        onlinePlayContainer.add(new Label("Actually, there is no online game yet.\n QAQ"), 0, 1);


        //Initialize the Play Selector

        playSelector.addPage("Local", localPlayContainer);
        playSelector.addPage("LAN", LANPlayContainer);
        playSelector.addPage("Online", onlinePlayContainer);
        playSelector.resetSelectorWidth(120);
        playSelector.init();

        localPlayPane = new FlowPane();
        SmoothishScrollPane container = new SmoothishScrollPane(localPlayPane);
        GridPane.setHgrow(container, Priority.ALWAYS);
        GridPane.setVgrow(container, Priority.ALWAYS);
        localPlayContainer.add(container, 0, 1);
        //Loading default selections

        newGamePreview = new GamePreviewPane(gameSystem, theme, GamePreviewPane.PreviewType.NEW_GAME);
        localPlayPane.getChildren().add(newGamePreview);

        loadGamePreview = new GamePreviewPane(gameSystem, theme, GamePreviewPane.PreviewType.LOAD_GAME_FROM_FILE);
        localPlayPane.getChildren().add(loadGamePreview);

        initLayout();
        initRelations();
        //TEST
        //END TEST
    }

    private void initLayout() {
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


    private void initLoadGamePreview() {

    }

    @Override
    public void update() {

    }
}
