package view.contentpages;

import component.TitleLabel;
import component.selector.SelectorPage;
import controller.GameSystem;
import controller.logger.Log0j;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import res.literal.LiteralConstants;
import view.Theme;
import view.gamepages.GamePageLocal;

public class PlayPage implements Updatable {
    public static final double SELECTOR_WIDTH = 180;

    public final GridPane root;
    public final StackPane viewPaneStack;
    public final SelectorPage selectorPage;

    // Secondary views
    public final GridPane localPlayPane;
    public final SelectorPage localPlaySelector;
    public final GridPane localPlayNewPane;
    public final GridPane localPlayLoadPane;

    public final GridPane onlinePlayPane;


    public final Button newLocalGameButton;
    public final Button loadLocalGameButton;

    public final GameSystem gameSystem;
    public final Theme theme;


    public PlayPage(GameSystem gameSystem, Theme theme) {
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

        viewPaneStack = new StackPane();
        theme.bindToFrontPane(viewPaneStack.backgroundProperty());
        root.add(viewPaneStack, 0, 1);


        //Initialize all secondary panes.
        localPlayPane = new GridPane();
        localPlaySelector = new SelectorPage(theme);
        localPlayNewPane = new GridPane();
        localPlayLoadPane = new GridPane();
        initLocalPlayPane();

        onlinePlayPane = new GridPane();
        initOnlinePlayPane();
        viewPaneStack.getChildren().addAll(localPlayPane, onlinePlayPane);




        //Initialize the selector
        selectorPage = new SelectorPage(null, theme);
        selectorPage.addSelection("Local", localPlayPane);
        selectorPage.addSelection("Online", onlinePlayPane);

        GridPane.setRowSpan(selectorPage.root, 2);
        root.add(selectorPage.root, 0, 1);


        //Setting
        {
            ColumnConstraints columnConstraints[] = new ColumnConstraints[2];
            for (int i = 0; i < 2; i++) {
                columnConstraints[i] = new ColumnConstraints();
                columnConstraints[i].setHgrow(Priority.ALWAYS);
                root.getColumnConstraints().add(columnConstraints[i]);
            }
            columnConstraints[0].setMinWidth(SELECTOR_WIDTH);
            columnConstraints[0].setMaxWidth(SELECTOR_WIDTH);
        }



        //Buttons need not only to be initialized, but also added to the pane.
        newLocalGameButton = new Button(LiteralConstants.PlayLocalText.toString());
        initPlayLocalGameButton();

        loadLocalGameButton = new Button(LiteralConstants.LoadGameText.toString());
        initLoadGameButton();

    }

    //todo: complete the method
    private void initLocalPlayPane() {
        localPlayPane.add(new TitleLabel("Play Local Game", theme), 0, 0);
        localPlaySelector.addSelection("New Game", localPlayNewPane);
        localPlaySelector.addSelection("Load Game", localPlayLoadPane);
    }

    //todo: complete the method
    private void initOnlinePlayPane() {
        onlinePlayPane.add(new TitleLabel("Play Online Game", theme), 0, 0);
    }

    private void initPlayLocalGameButton() {
        //TEST LOCAL GAME
        newLocalGameButton.setPrefHeight(75);
        newLocalGameButton.setPrefWidth(300);

        newLocalGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                GamePageLocal gameLocalPage = new GamePageLocal(gameSystem.startNewGame(), theme);
                Image iconImg = new Image("/res/icons/App.png");
                Stage gameStage = new Stage();
                gameStage.setScene(new Scene(gameLocalPage.root));
                gameStage.setTitle("LocalPlay");
                gameStage.getIcons().add(iconImg);

                gameStage.setMinWidth(GamePageLocal.MIN_WIDTH);
                gameStage.setMinHeight(GamePageLocal.MIN_HEIGHT);

                gameStage.show();
                Log0j.writeLog("LocalPlay (New Game) initialized.");
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
                //Theoretically, a new prompt asking for choosing the saves should pop up.
                //todo: change default index property
                SimpleIntegerProperty indexProperty = new SimpleIntegerProperty(2);

                if (indexProperty.intValue() != -1) {
                    GamePageLocal gameLocalPage = new GamePageLocal(gameSystem.loadGame(indexProperty.intValue(), false), theme);
                    Image iconImg = new Image("/res/icons/App.png");
                    Stage gameStage = new Stage();
                    gameStage.setScene(new Scene(gameLocalPage.root));
                    gameStage.setTitle("LocalPlay");
                    gameStage.getIcons().add(iconImg);
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
