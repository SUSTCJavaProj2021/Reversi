package com.demo.reversi.component;

import com.demo.reversi.themes.Theme;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

public class TextLabel extends Label {

    private final Theme theme;

    public TextLabel(Theme theme){
        this.theme = theme;
        initRelations();
    }

    public TextLabel(String text, Theme theme){
        super(text);
        this.theme = theme;
        initRelations();
        setTooltip(new Tooltip(text));
    }

    private void initRelations(){
        fontProperty().bind(theme.textFontFamilyPR());
        textFillProperty().bind(theme.textFontPaintPR());
    }
}
