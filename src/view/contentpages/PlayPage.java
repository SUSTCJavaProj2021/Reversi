package view.contentpages;

import component.TitleLabel;
import component.selector.SelectorPage;
import controller.GameSystem;
import controller.logger.Log0j;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;
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
import view.prompts.LoadGamePrompt;

public class PlayPage implements Updatable{
    public static final double SELECTOR_WIDTH = 180;

    public final GridPane root;
    public final SelectorPage selectorPage;

    public Button newLocalGameButton;
    public Button loadLocalGameButton;

    public final GameSystem gameSystem;
    public final Theme theme;


    public PlayPage(GameSystem gameSystem, Theme theme) {
        this.gameSystem = gameSystem;
        this.theme = theme;

        root = new GridPane();
        root.add(new TitleLabel(LiteralConstants.PlayPageTitle.toString(), theme), 0, 0);
        GridPane.setColumnSpan(root.getChildren().get(0), 2);

        //Selector
        selectorPage = new SelectorPage(null, theme);

        GridPane.setRowSpan(selectorPage.root, 2);
        root.add(selectorPage.root, 0, 1);

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

        initPlayLocalGameButton();
        initLoadGameButton();

    }

    private void initPlayLocalGameButton() {
        //TEST LOCAL GAME
        newLocalGameButton = new Button(LiteralConstants.PlayLocalText.toString());
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
        root.add(newLocalGameButton, 1, 1);
    }

    private void initLoadGameButton() {
//TEST LOAD GAME
        loadLocalGameButton = new Button(LiteralConstants.LoadGameText.toString());
        loadLocalGameButton.setPrefHeight(75);
        loadLocalGameButton.setPrefWidth(300);

        loadLocalGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Theoretically, a new prompt asking for choosing the saves should pop up.
                //todo: change default index property
                SimpleIntegerProperty indexProperty = new SimpleIntegerProperty(2);
                Stage promptStage = new Stage();
                promptStage.setScene(new Scene(new LoadGamePrompt(gameSystem, indexProperty).root));
                promptStage.showAndWait();

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
        root.add(loadLocalGameButton, 1, 2);
    }

    @Override
    public void update() {

    }
}
