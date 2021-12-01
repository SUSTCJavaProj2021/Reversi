package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import res.language.LiteralConstants;

public class PlayPage {
    public Scene scene;
    public GridPane optionPane;

    public Button playLocalButton;

    public PlayPage(int resWidth, int resHeight) {
        optionPane = new GridPane();
        playLocalButton = new Button(LiteralConstants.PlayLocalText.getText());

        scene = new Scene(optionPane, resWidth, resHeight);

        optionPane.add(new HBox(100), 0, 0);
        optionPane.add(playLocalButton, 0, 1);
        playLocalButton.setAlignment(Pos.CENTER);
    }
}
