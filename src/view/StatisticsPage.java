package view;

import controller.GameController;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class StatisticsPage {
    public GridPane root;

    public StatisticsPage(GameController controller){
        root = new GridPane();

        root.setBackground(new Background(new BackgroundFill(Color.web("26272F"), null, null)));
    }
}
