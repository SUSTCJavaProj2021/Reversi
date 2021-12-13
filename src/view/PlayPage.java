package view;

import component.pagecomponents.AdaptiveStyleButton;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import res.language.LiteralConstants;

public class PlayPage {
    public BorderPane root;

    public Button playLocalButton;

    public PlayPage() {
        root = new BorderPane();
        playLocalButton = new Button(LiteralConstants.PlayLocalText.toString());

        root.setCenter(playLocalButton);
    }
}
