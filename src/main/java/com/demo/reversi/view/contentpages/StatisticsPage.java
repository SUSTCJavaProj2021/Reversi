package com.demo.reversi.view.contentpages;

import com.demo.reversi.component.TitleLabel;
import com.demo.reversi.controller.SimpleGameSystem;
import com.demo.reversi.res.lang.LiteralConstants;
import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.Updatable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class StatisticsPage implements Updatable {
    public final GridPane root;

    public final SimpleGameSystem gameSystem;
    public final Theme theme;

    public StatisticsPage(SimpleGameSystem gameSystem, Theme theme){
        this.gameSystem = gameSystem;
        this.theme = theme;
        root = new GridPane();
        root.add(new TitleLabel(LiteralConstants.StatisticsPageTitle.toString(), theme), 0, 0);

        root.add(new Label("Well played!"),0, 1);
    }

    @Override
    public void update() {

    }
}
