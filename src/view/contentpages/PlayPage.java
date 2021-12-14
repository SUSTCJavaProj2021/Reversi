package view.contentpages;

import component.TitleLabel;
import controller.GameSystem;
import controller.Log0j;
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
    public GridPane root;

    public Button playLocalButton;
    public Button loadGameButton;

    public PlayPage(GameSystem gameSystem) {
        root = new GridPane();
        root.add(new TitleLabel("Play"), 0, 0);


        playLocalButton = new Button(LiteralConstants.PlayLocalText.toString());
        playLocalButton.setPrefHeight(75);
        playLocalButton.setPrefWidth(300);

        loadGameButton = new Button(LiteralConstants.LoadGameText.toString());
        loadGameButton.setPrefHeight(75);
        loadGameButton.setPrefWidth(300);


        GridPane.setHalignment(playLocalButton, HPos.CENTER);
        GridPane.setHalignment(loadGameButton, HPos.CENTER);
        root.add(playLocalButton, 0, 1);
        root.add(loadGameButton, 0, 2);

        {
            ColumnConstraints c = new ColumnConstraints();
            c.setHgrow(Priority.ALWAYS);
            c.setPercentWidth(100);

            root.getColumnConstraints().add(c);
        }
        playLocalButton.setOnAction(new EventHandler<ActionEvent>() {
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

    }
}
