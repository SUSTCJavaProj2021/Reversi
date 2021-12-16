package component.selector;

import javafx.scene.control.Label;
import res.icons.Icon;
import view.Theme;

public class MainSelectorPane extends SelectorPane{
    public static final double VIEWCOVER_CORNER_RADII = 10;
    public static final double VIEWCOVER_HORIZONTAL_DIST = 20;
    public static final double VIEWCOVER_VERTICAL_DIST = 50;

    public MainSelectorPane(Label titleLabel, Theme theme){
        super(titleLabel, theme);
        viewCoverTop.setMinHeight(VIEWCOVER_HORIZONTAL_DIST);
        viewCoverTop.setMaxHeight(VIEWCOVER_HORIZONTAL_DIST);
        viewCoverBottom.setMinHeight(VIEWCOVER_HORIZONTAL_DIST);
        viewCoverBottom.setMaxHeight(VIEWCOVER_HORIZONTAL_DIST);

        viewCoverLeft.setMinWidth(VIEWCOVER_VERTICAL_DIST);
        viewCoverLeft.setMaxWidth(VIEWCOVER_VERTICAL_DIST);
        viewCoverRight.setMinWidth(VIEWCOVER_VERTICAL_DIST);
        viewCoverRight.setMaxWidth(VIEWCOVER_VERTICAL_DIST);
    }

    @Override
    public void init() {
        selector.setExitButton(new Icon("/res/icons/Exit.png"));
        selector.init();
    }
}
