package view;

import controller.GameController;
import controller.GameSystem;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class StatisticsPage {
    public GridPane root;

    public StatisticsPage(GameSystem system){
        root = new GridPane();
        root.add(new TextField("Well played!"),0, 0);

        root.setBackground(new Background(new BackgroundFill(Color.web("26272F"), null, null)));
    }
}
