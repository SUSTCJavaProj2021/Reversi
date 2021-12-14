package view.contentpages;

import component.TitleLabel;
import controller.GameSystem;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class SaveAndLoadPage {
    public GridPane root;

    public SaveAndLoadPage(GameSystem gameSystem) {
        root = new GridPane();
        root.add(new TitleLabel("Save And Load"), 0, 0);
    }
}
