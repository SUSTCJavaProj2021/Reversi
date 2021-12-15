package view.contentpages;

import component.TitleLabel;
import controller.GameSystem;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import view.Theme;

public class StatisticsPage {
    public final GridPane root;

    public final GameSystem gameSystem;
    public final Theme theme;

    public StatisticsPage(GameSystem gameSystem, Theme theme){
        this.gameSystem = gameSystem;
        this.theme = theme;
        root = new GridPane();
        root.add(new TitleLabel("Statistics", theme), 0, 0);
        root.add(new TextField("Well played!"),0, 1);
    }
}
