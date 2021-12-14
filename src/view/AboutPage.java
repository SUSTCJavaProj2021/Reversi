package view;

import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class AboutPage {
    public GridPane root;

    public AboutPage() {
        root = new GridPane();
        Label ver = new Label("Version: 0.01; All controls are hard coded.");
        ver.setTextFill(Color.WHITE);
        Label aut = new Label("Author: sorrymaker");
        aut.setTextFill(Color.WHITE);
        root.add(ver, 0, 0);
        root.add(aut, 0, 1);
        root.setBackground(new Background(new BackgroundFill(Color.web("26272F"), null, null)));
    }
}
