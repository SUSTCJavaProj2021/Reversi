package com.demo.reversi.component.switches;

import com.demo.reversi.themes.Theme;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class IndicatedToggleSwitch extends HBox {
    public static final double SPACING = 15;
    public final ToggleSwitch toggleSwitch;
    public final Label label;

    private final Theme theme;

    public IndicatedToggleSwitch(Theme theme) {
        this(theme, null, null);
    }

    public IndicatedToggleSwitch(Theme theme, String onText, String offText) {
        super(SPACING);
        setCache(true);
        this.theme = theme;

        toggleSwitch = new ToggleSwitch(theme);

        label = new Label();
        label.fontProperty().bind(theme.textFontFamilyPR());
        label.textFillProperty().bind(theme.textFontPaintPR());

        getChildren().add(toggleSwitch);
        getChildren().add(label);

        initRelations(onText, offText);
    }


    private void initRelations(String onText, String offText) {
        if (onText == null) {
            onText = "On";
        }
        if (offText == null) {
            offText = "Off";
        }
        final String onText1 = onText;
        final String offText1 = offText;
        label.textProperty().bind(Bindings.createObjectBinding(() -> {
            if (toggleSwitch.switchedOnProperty().getValue()) {
                return onText1;
            } else {
                return offText1;
            }
        }, toggleSwitch.switchedOnProperty()));
    }

    public BooleanProperty switchedOnProperty() {
        return toggleSwitch.switchedOnProperty();
    }

}
