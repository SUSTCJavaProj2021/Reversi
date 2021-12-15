package view.contentpages;

import component.TitleLabel;
import controller.GameSystem;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import view.Theme;

public class SettingsPage {
    public final GridPane root;

    public final GameSystem gameSystem;
    public final Theme theme;

    public SettingsPage(GameSystem gameSystem, Theme theme) {
        this.gameSystem = gameSystem;
        this.theme = theme;
        root = new GridPane();
        root.add(new TitleLabel("Settings", theme), 0, 0);
    }
}
