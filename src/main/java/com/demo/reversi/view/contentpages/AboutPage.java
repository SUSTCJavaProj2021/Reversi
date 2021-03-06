package com.demo.reversi.view.contentpages;

import com.demo.reversi.component.TextLabel;
import com.demo.reversi.component.TitleLabel;
import com.demo.reversi.res.lang.LiteralConstants;
import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.Updatable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class AboutPage implements Updatable {
    public final GridPane root;

    public final Theme theme;

    public AboutPage(Theme theme) {
        this.theme = theme;
        root = new GridPane();
        root.add(new TitleLabel(LiteralConstants.AboutPageTitle.toString(), theme), 0, 0);

        TextLabel ver = new TextLabel("Version: 0.01\n" +
                "All controls are hard coded.\n" +
                "All Controls are coded using NATIVE javafx controls. Animations are customized to suit Fluent Design Style.", theme);

        TextLabel aut = new TextLabel("Authors (In alphabetical order): IskXCr, kchgod.", theme);

        root.add(ver, 0, 1);
        root.add(aut, 0, 2);

    }

    @Override
    public void update() {

    }

    @Override
    public void performOnCloseAction() {

    }
}
