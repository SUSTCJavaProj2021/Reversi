package view.contentpages;

import component.TitleLabel;
import component.selector.SelectorPage;
import controller.GameSystem;
import controller.logger.Log0j;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import res.literal.LiteralConstants;
import view.gamepages.GamePageLocal;

public class PlayPage {
    public static final double SELECTOR_WIDTH = 180;

    public GridPane root;
    public SelectorPage selectorPage;

    public Button newLocalGameButton;
    public Button loadLocalGameButton;


    public PlayPage(GameSystem gameSystem) {
        root = new GridPane();
        root.add(new TitleLabel("Play"), 0, 0);
        GridPane.setColumnSpan(root.getChildren().get(0), 2);
        selectorPage = new SelectorPage(null);

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


        //TEST LOCAL GAME
        newLocalGameButton = new Button(LiteralConstants.PlayLocalText.toString());
        newLocalGameButton.setPrefHeight(75);
        newLocalGameButton.setPrefWidth(300);

        newLocalGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                GamePageLocal gameLocalPage = new GamePageLocal(gameSystem.startNewGame());
                Image iconImg = new Image("/res/icons/App.png");
                Stage s = new Stage();
                s.setScene(new Scene(gameLocalPage.root));
                s.setTitle("LocalPlay");
                s.getIcons().add(iconImg);

                s.setMinWidth(GamePageLocal.MIN_WIDTH);
                s.setMinHeight(GamePageLocal.MIN_HEIGHT);

                s.show();
                Log0j.writeLog("LocalPlay (New Game) initialized.");
            }
        });


        //TEST LOAD GAME
        loadLocalGameButton = new Button(LiteralConstants.LoadGameText.toString());
        loadLocalGameButton.setPrefHeight(75);
        loadLocalGameButton.setPrefWidth(300);

        loadLocalGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Theoretically, a new prompt asking for choosing the saves should pop up.

                GamePageLocal gameLocalPage = new GamePageLocal(gameSystem.loadGame(0, false));
                Image iconImg = new Image("/res/icons/App.png");
                Stage s = new Stage();
                s.setScene(new Scene(gameLocalPage.root));
                s.setTitle("LocalPlay");
                s.getIcons().add(iconImg);

                s.setMinWidth(GamePageLocal.MIN_WIDTH);
                s.setMinHeight(GamePageLocal.MIN_HEIGHT);

                s.show();
                Log0j.writeLog("LocalPlay (Load Game) initialized.");
            }
        });


        GridPane.setHalignment(newLocalGameButton, HPos.CENTER);
        GridPane.setHalignment(loadLocalGameButton, HPos.CENTER);
        root.add(newLocalGameButton, 1, 1);
        root.add(loadLocalGameButton, 1, 2);

    }
}
