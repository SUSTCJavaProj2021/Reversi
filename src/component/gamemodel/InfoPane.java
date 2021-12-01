package component.gamemodel;

import controller.Player;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class InfoPane extends GridPane {
    public static final int MIN_WIDTH = 200;

    public InfoPane(Player player) {
        super();
        this.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        this.setMinWidth(MIN_WIDTH);
    }
}
