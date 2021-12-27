package com.demo.reversi.view.contentpages;

import com.demo.reversi.component.MetroButton;
import com.demo.reversi.component.TitleLabel;
import com.demo.reversi.component.panes.SmoothishScrollPane;
import com.demo.reversi.component.selector.SelectorPane;
import com.demo.reversi.controller.basic.GameSystem;
import com.demo.reversi.controller.interfaces.GameControllerLayer;
import com.demo.reversi.controller.interfaces.GameSystemLayer;
import com.demo.reversi.controller.local.SimpleGameSystem;
import com.demo.reversi.res.lang.LiteralConstants;
import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.Updatable;
import com.demo.reversi.view.gamepages.GamePreviewPane;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.List;

public class PlayPage implements Updatable {
    public static final double SELECTOR_WIDTH = 180;

    public final GridPane root;
    public final SelectorPane playSelector;

    // Secondary views
    public final GridPane localPlayContainer;
    public final GridPane LANPlayContainer;
    public final GridPane onlinePlayContainer;

    public final FlowPane localPlayPane;
    public final MetroButton localRefBtn;

    public final GamePreviewPane tutorialPreview;
    public final GamePreviewPane newGamePreview;    //For creating a new game.
    public final GamePreviewPane loadGamePreview;   //For loading game from file.

    public final GameSystemLayer gameSystem;
    public final Theme theme;


    public PlayPage(GameSystemLayer gameSystem, Theme theme) {
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
        root.setCache(true);
        root.add(new TitleLabel(LiteralConstants.PlayPageTitle.toString(), theme), 0, 0, 2, 1);
        playSelector = new SelectorPane(theme);
        root.add(playSelector, 0, 1);


        //Initialize all secondary panes.

        localRefBtn = new MetroButton("Refresh List", theme);
        localPlayContainer = new GridPane();
        localPlayContainer.add(new HBox(100, new TitleLabel("Play Game on Local Machine", theme), localRefBtn), 0, 0);

        LANPlayContainer = new GridPane();
        LANPlayContainer.add(new TitleLabel("Play Game in Local Area Network", theme), 0, 0);
        Label labelLAN = new Label("Actually, there is no LAN game yet.\nQAQ");
        labelLAN.setFont(theme.textFontFamilyPR().getValue());
        labelLAN.setTextFill(theme.textFontPaintPR().getValue());
        LANPlayContainer.add(labelLAN, 0, 1);

        onlinePlayContainer = new GridPane();
        onlinePlayContainer.add(new TitleLabel("Play Online Game", theme), 0, 0);
        Label labelONLINE = new Label("Actually, there is no online game yet.\nQAQ");
        labelONLINE.setFont(theme.textFontFamilyPR().getValue());
        labelONLINE.setTextFill(theme.textFontPaintPR().getValue());
        onlinePlayContainer.add(labelONLINE, 0, 1);


        //Initialize the Play Selector

        playSelector.addPage("Local", localPlayContainer);
        playSelector.addPage("LAN", LANPlayContainer);
        playSelector.addPage("Online", onlinePlayContainer);
        playSelector.resetSelectorWidth(120);
        playSelector.init();

        localPlayPane = new FlowPane(5, 10);
        SmoothishScrollPane container = new SmoothishScrollPane(localPlayPane);
        GridPane.setHgrow(container, Priority.ALWAYS);
        GridPane.setVgrow(container, Priority.ALWAYS);
        localPlayContainer.add(container, 0, 1);
        //Loading default selections

        tutorialPreview = new GamePreviewPane(gameSystem, theme, GamePreviewPane.PreviewType.TUTORIAL);
        localPlayPane.getChildren().add(tutorialPreview);

        newGamePreview = new GamePreviewPane(gameSystem, theme, GamePreviewPane.PreviewType.NEW_GAME);
        localPlayPane.getChildren().add(newGamePreview);

        loadGamePreview = new GamePreviewPane(gameSystem, theme, GamePreviewPane.PreviewType.LOAD_GAME_FROM_FILE);
        localPlayPane.getChildren().add(loadGamePreview);

        initLayout();
        initRelations();


        loadLocalGamePreview();

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

        localRefBtn.setOnAction(ActionEvent -> {
            refreshLocalGamePreview();
        });

    }


    private void loadLocalGamePreview() {
        List<GameControllerLayer> gameControllers = gameSystem.queryGameControllerAllSorted();
        for (GameControllerLayer controller : gameControllers) {
            localPlayPane.getChildren().add(new GamePreviewPane(gameSystem, controller, theme));
        }
    }

    private void refreshLocalGamePreview() {
        //Clear all the children except for the first two panes
        if (localPlayPane.getChildren().size() > 2) {
            localPlayPane.getChildren().subList(2, localPlayPane.getChildren().size()).clear();
        }

        //Load new panes
        List<GameControllerLayer> gameControllers = gameSystem.queryGameControllerAllSorted();
        for (GameControllerLayer controller : gameControllers) {
            localPlayPane.getChildren().add(new GamePreviewPane(gameSystem, controller, theme));
        }
    }

    @Override
    public void update() {
        refreshLocalGamePreview();
    }
}
