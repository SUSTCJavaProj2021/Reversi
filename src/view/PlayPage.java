package view;

import controller.GameController;
import controller.GameSystem;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import res.language.LiteralConstants;

public class PlayPage {
    public GridPane root;

    public Button playLocalButton;
    public Button loadGameButton;

    public PlayPage(GameSystem system) {
        root = new GridPane();
        playLocalButton = new Button(LiteralConstants.PlayLocalText.toString());
        playLocalButton.setPrefHeight(75);
        playLocalButton.setPrefWidth(300);

        loadGameButton = new Button(LiteralConstants.LoadGameText.toString());
        loadGameButton.setPrefHeight(75);
        loadGameButton.setPrefWidth(300);


        GridPane.setHalignment(playLocalButton, HPos.CENTER);
        GridPane.setHalignment(loadGameButton, HPos.CENTER);
        root.add(playLocalButton, 0, 0);
        root.add(loadGameButton, 0, 1);

        {
            ColumnConstraints c = new ColumnConstraints();
            c.setHgrow(Priority.ALWAYS);
            c.setPercentWidth(100);

            root.getColumnConstraints().add(c);
        }

        root.setBackground(new Background(new BackgroundFill(Color.web("26272F"), null, null)));
    }
}
