package com.demo.reversi.view.contentpages;

import com.demo.reversi.component.MetroButton;
import com.demo.reversi.component.TitleLabel;
import com.demo.reversi.component.panes.SmoothishScrollPane;
import com.demo.reversi.controller.SimpleGameSystem;
import com.demo.reversi.res.lang.LiteralConstants;
import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.Updatable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class SettingsPage implements Updatable {
    public static final double PREF_CONTAINER_HEIGHT = 100;
    public final GridPane root;
    public final SmoothishScrollPane scrollPane;
    public final GridPane contentWrapper;
    public final GridPane configWrapper;

    public final SimpleGameSystem gameSystem;
    public final Theme theme;

    public SettingsPage(SimpleGameSystem gameSystem, Theme theme) {
        this.gameSystem = gameSystem;
        this.theme = theme;
        root = new GridPane();
        contentWrapper = new GridPane();
        scrollPane = new SmoothishScrollPane(contentWrapper);
        configWrapper = new GridPane();

        {
            {
                RowConstraints[] constraints = new RowConstraints[3];
                for (int i = 0; i < 3; i++) {
                    constraints[i] = new RowConstraints();
                    root.getRowConstraints().add(constraints[i]);
                }
                constraints[1].setVgrow(Priority.ALWAYS);
            }
            {
                ColumnConstraints[] constraints = new ColumnConstraints[3];
                for (int i = 0; i < 3; i++) {
                    constraints[i] = new ColumnConstraints();
                    constraints[i].setHgrow(Priority.ALWAYS);
                    root.getColumnConstraints().add(constraints[i]);
                }
            }
        }
        root.add(new TitleLabel(LiteralConstants.SettingsPageTitle.toString(), theme), 0, 0);
        root.add(scrollPane, 0, 1);
        root.add(configWrapper, 0, 2);

        //Test ColorPicker

        initContent();
        initConfig();

    }

    public void initContent() {
        initThemeColorPicker();
    }

    public void initConfig() {
        contentWrapper.add(new MetroButton("Click to do nothing", theme), 0, 0);
    }

    public void initThemeColorPicker() {
        HBox container = new HBox();
        container.setPrefWidth(Control.USE_COMPUTED_SIZE);
        container.setPrefHeight(PREF_CONTAINER_HEIGHT);

        ColorPicker cp = new ColorPicker(Theme.defaultThemeColor);
        theme.themeColorPR().bind(cp.valueProperty());

        Label label = new Label("ThemeColor");
        label.fontProperty().bind(theme.textFontFamilyPR());
        label.textFillProperty().bind(theme.textFontPaintPR());

        Separator s = new Separator();
        s.setOpacity(0);

        container.getChildren().addAll(label, s, cp);
        contentWrapper.add(container, 0, 1);
    }

    @Override
    public void update() {

    }
}
