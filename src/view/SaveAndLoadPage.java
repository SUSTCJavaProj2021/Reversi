package view;

import controller.GameController;
import controller.GameSystem;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class SaveAndLoadPage {
    public GridPane root;

    public SaveAndLoadPage(GameSystem system) {
        root = new GridPane();
        root.setBackground(new Background(new BackgroundFill(Color.web("26272F"), null, null)));
    }
}
