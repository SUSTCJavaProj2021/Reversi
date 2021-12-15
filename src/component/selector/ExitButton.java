package component.selector;

import javafx.scene.image.ImageView;
import res.literal.LiteralConstants;
import view.Theme;

public class ExitButton extends SelectorButton {
    public ExitButton(ImageView icon, Theme theme) {
        super(LiteralConstants.ExitButtonText.toString(), null, icon, theme);
    }
}
