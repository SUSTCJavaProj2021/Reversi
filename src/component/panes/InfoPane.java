package component.panes;

import controller.Player;
import javafx.scene.layout.GridPane;
import view.Theme;

public class InfoPane extends GridPane {
    public static final int MIN_WIDTH = 200;

    public Theme theme;

    public InfoPane(Player player, Theme theme) {
        super();
        this.theme = theme;

        theme.bindToPaintBackground(backgroundProperty());
        this.setMinWidth(MIN_WIDTH);
    }
}
