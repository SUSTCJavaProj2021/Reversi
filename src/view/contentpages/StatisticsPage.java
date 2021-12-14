package view.contentpages;

import component.TitleLabel;
import controller.GameSystem;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class StatisticsPage {
    public GridPane root;

    public StatisticsPage(GameSystem gameSystem){
        root = new GridPane();
        root.add(new TitleLabel("Statistics"), 0, 0);
        root.add(new TextField("Well played!"),0, 1);
    }
}
