package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import res.language.LiteralConstants;

public class PlayPage {
    public GridPane rootPane;

    public Button playLocalButton;
    public Button backButton;

    public PlayPage() {
        rootPane = new GridPane();
        playLocalButton = new Button(LiteralConstants.PlayLocalText.getText());
        backButton = new Button(LiteralConstants.BackButtonText.getText());

        rootPane.add(new HBox(100), 0, 0);
        rootPane.add(backButton, 0, 1);
        rootPane.add(playLocalButton, 0, 2);
        playLocalButton.setAlignment(Pos.CENTER);

    }
}
