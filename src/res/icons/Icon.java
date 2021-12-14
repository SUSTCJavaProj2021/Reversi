package res.icons;

import component.selector.SelectorButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Icon extends ImageView {
    public Icon(String srcPath){
        super(new Image(srcPath));
        setPreserveRatio(true);
    }
}
