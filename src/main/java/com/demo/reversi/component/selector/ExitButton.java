package com.demo.reversi.component.selector;

import com.demo.reversi.res.lang.LiteralConstants;
import com.demo.reversi.themes.Theme;
import javafx.scene.image.ImageView;

public class ExitButton extends SelectorButton {
    public ExitButton(ImageView icon, Theme theme) {
        super(LiteralConstants.ExitButtonText.toString(), null, icon, theme);
    }
}
