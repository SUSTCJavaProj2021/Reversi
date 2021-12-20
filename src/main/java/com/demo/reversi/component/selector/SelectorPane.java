package com.demo.reversi.component.selector;

import com.demo.reversi.logger.Log0j;
import com.demo.reversi.res.icons.Icon;
import com.demo.reversi.themes.Theme;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URISyntaxException;

public class SelectorPane extends GridPane {
    public static final double VIEWCOVER_CORNER_RADII = 10;
    public static final double VIEWCOVER_HORIZONTAL_DIST = 20;
    public static final double VIEWCOVER_VERTICAL_DIST = 20;
    public static final double SELECTOR_WIDTH = 180;

    public final Selector selector;

    public final BorderPane viewCoverPane;
    public final HBox viewCoverTop;
    public final HBox viewCoverBottom;
    public final VBox viewCoverLeft;
    public final VBox viewCoverRight;

    public final StackPane viewPaneStack;

    public final Theme theme;

    public SelectorPane(Theme theme) {
        this(null, theme);
    }

    public SelectorPane(Label titleLabel, Theme theme) {
        this.theme = theme;

        //Initialize background

        //Initialize view cover
        viewCoverPane = new BorderPane();

        //Initialize Top and Bottom cover
        viewCoverTop = new HBox();
        viewCoverBottom = new HBox();

        viewCoverTop.setMinHeight(VIEWCOVER_HORIZONTAL_DIST);
        viewCoverTop.setMaxHeight(VIEWCOVER_HORIZONTAL_DIST);
        viewCoverBottom.setMinHeight(VIEWCOVER_HORIZONTAL_DIST);
        viewCoverBottom.setMaxHeight(VIEWCOVER_HORIZONTAL_DIST);

        viewCoverTop.setMinWidth(HBox.USE_COMPUTED_SIZE);
        viewCoverBottom.setMinWidth(HBox.USE_COMPUTED_SIZE);

        viewCoverPane.setTop(viewCoverTop);
        viewCoverPane.setBottom(viewCoverBottom);

        //Initialize Left and Right cover
        viewCoverLeft = new VBox();
        viewCoverRight = new VBox();
        viewCoverLeft.setMinWidth(VIEWCOVER_VERTICAL_DIST);
        viewCoverLeft.setMaxWidth(VIEWCOVER_VERTICAL_DIST);
        viewCoverRight.setMinWidth(VIEWCOVER_VERTICAL_DIST);
        viewCoverRight.setMaxWidth(VIEWCOVER_VERTICAL_DIST);

        viewCoverLeft.setMinHeight(HBox.USE_COMPUTED_SIZE);
        viewCoverRight.setMinHeight(HBox.USE_COMPUTED_SIZE);

        viewCoverPane.setLeft(viewCoverLeft);
        viewCoverPane.setRight(viewCoverRight);

        //Final adjust for viewCoverPane
        GridPane.setHgrow(viewCoverPane, Priority.ALWAYS);
        GridPane.setVgrow(viewCoverPane, Priority.ALWAYS);
        theme.bindToFrontPane(viewCoverPane.backgroundProperty());
        Log0j.writeLog("ViewCover Pane loaded.");

        //Initialize View stack
        viewPaneStack = new StackPane();
        viewCoverPane.setCenter(viewPaneStack);
        GridPane.setHgrow(viewPaneStack, Priority.ALWAYS);
        GridPane.setVgrow(viewPaneStack, Priority.ALWAYS);
        Log0j.writeLog("View Pane loaded.");

        //Load selector
        if (titleLabel != null) {
            selector = new Selector(titleLabel, theme);
        } else {
            selector = new Selector(theme);
        }

        //Initialize SelectorPane
        ColumnConstraints selectorColCNSTR = new ColumnConstraints();
        selectorColCNSTR.setMinWidth(SELECTOR_WIDTH);
        selectorColCNSTR.setMaxWidth(SELECTOR_WIDTH);
        getColumnConstraints().add(selectorColCNSTR);

        add(selector,0,0);
        add(viewCoverPane,1,0);
        GridPane.setHgrow(this,Priority.ALWAYS);
        GridPane.setVgrow(this, Priority.ALWAYS);

        //

        Log0j.writeLog("SelectorPane Initialized.");
    }

    public void addPage(String id, Node node, Icon icon) {
        selector.addSelection(id, node, icon);
        viewPaneStack.getChildren().add(node);
    }

    public void addPage(String id, Node node) {
        addPage(id, node, null);
    }

    public void resetSelectorWidth(double width){
        getColumnConstraints().get(0).setMinWidth(width);
        getColumnConstraints().get(0).setMaxWidth(width);
    }

    public void init(){
        selector.init();
    }
}
