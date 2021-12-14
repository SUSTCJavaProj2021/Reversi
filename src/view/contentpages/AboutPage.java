package view.contentpages;

import component.TitleLabel;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import res.literal.LiteralConstants;

public class AboutPage {
    public GridPane root;

    public AboutPage() {
        root = new GridPane();
        root.add(new TitleLabel("About"), 0, 0);

        Label ver = new Label("Version: 0.01; All controls are hard coded.");
        ver.setTextFill(Color.WHITE);
        Label aut = new Label("Author: sorrymaker");
        aut.setTextFill(Color.WHITE);
        root.add(ver, 0, 1);
        root.add(aut, 0, 2);

    }
}
