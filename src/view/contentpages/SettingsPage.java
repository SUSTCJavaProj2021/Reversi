package view.contentpages;

import component.TitleLabel;
import controller.GameSystem;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class SettingsPage {
    public GridPane root;

    public SettingsPage(GameSystem gameSystem) {
        root = new GridPane();
        root.add(new TitleLabel("Settings"), 0, 0);
    }
}
