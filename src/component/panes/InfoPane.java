package component.panes;

import controller.Player;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import view.Theme;

public class InfoPane extends GridPane {
    public static final int MIN_WIDTH = 200;

    public Theme theme;

    public InfoPane(Player player, Theme theme) {
        super();
        this.theme = theme;

        theme.bindPaintBackground(backgroundProperty());
        this.setMinWidth(MIN_WIDTH);
    }
}
