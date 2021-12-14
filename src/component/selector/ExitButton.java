package component.selector;

import javafx.scene.image.ImageView;
import res.literal.LiteralConstants;

public class ExitButton extends SelectorButton {
    public ExitButton(ImageView icon) {
        super(LiteralConstants.ExitButtonText.toString(), null, icon);
    }
}
