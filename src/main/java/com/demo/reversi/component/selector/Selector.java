package com.demo.reversi.component.selector;

import com.demo.reversi.themes.Theme;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class Selector extends GridPane{
    public static final double CORNER_RADII = 10;
    public static final double SELECTOR_WIDTH = 180;

    public VBox selectorContainer;

    public ArrayList<SelectorButton> buttonList;

    public SelectorButton currentSelectedButton;
    public ExitButton exitButton;

    public boolean hasBottom;

    public Theme theme;


    public Selector(Theme theme){
        this.theme = theme;

        new GridPane();
        {
            Separator separators[] = new Separator[2];
            for (int i = 0; i < 2; i++) {
                separators[i] = new Separator(Orientation.VERTICAL);
                separators[i].setOpacity(0);
                separators[i].setMinWidth(10);
            }
            add(separators[0], 0, 0);
            add(separators[1], 2, 0);
        }

        selectorContainer = new VBox();
        selectorContainer.setPrefWidth(SelectorButton.PREFERRED_WIDTH);
        selectorContainer.setMinWidth(VBox.USE_COMPUTED_SIZE);
        GridPane.setVgrow(selectorContainer, Priority.ALWAYS);
        GridPane.setHgrow(selectorContainer, Priority.ALWAYS);

        add(selectorContainer, 1, 0);


        buttonList = new ArrayList<SelectorButton>(0);
        currentSelectedButton = null;
        hasBottom = false;

        setPrefWidth(SelectorButton.PREFERRED_WIDTH + 20);
        //setBackground(new Background(new BackgroundFill(Color.rgb(0,0,0,0.05), new CornerRadii(CORNER_RADII), null)));
    }

    public Selector(Label title, Theme theme) {
        this(theme);
        if (title != null) {
            Separator separators[] = new Separator[2];
            for (int i = 0; i < 2; i++) {
                separators[i] = new Separator(Orientation.HORIZONTAL);
                separators[i].setMinHeight(15);
                separators[i].setMaxHeight(15);
                separators[i].setMinWidth(HBox.USE_COMPUTED_SIZE);
                separators[i].setOpacity(0);
            }
            selectorContainer.getChildren().addAll(separators[0], title, separators[1]);
        }
    }

    public void init() {
        setCurrentSelection(buttonList.get(0));
        if(!hasBottom){
            fillBottom();
        }
    }

    public void addSelection(String selectionText, Node node){
        addSelection(selectionText, node, null);
    }

    public void addSelection(String selectionText, Node node, ImageView icon) {
        SelectorButton button = new SelectorButton(selectionText, node, icon, theme);
        if (buttonList.size() != 0) {
            Separator separator = new Separator(Orientation.HORIZONTAL);
            separator.setMinHeight(5);
            separator.setMaxHeight(5);
            separator.setMinWidth(HBox.USE_COMPUTED_SIZE);
            separator.setOpacity(0);
            selectorContainer.getChildren().add(separator);
        }
        buttonList.add(button);
        selectorContainer.getChildren().add(button);

        //Initialize
        node.setVisible(false);

        //Deal with actions
        button.setOnAction(ActionEvent -> {
            setCurrentSelection(button);
        });
    }

    public void setCurrentSelection(SelectorButton button) {
        if (currentSelectedButton != button) {
            if (currentSelectedButton != null) {
                currentSelectedButton.setDeselected();
            }
            currentSelectedButton = button;
            currentSelectedButton.setSelected();
        }
//        Log0j.writeLog("Switched selection: " + currentSelectedButton.textProperty().getValue());
    }


    public void setExitButton(ImageView icon) {
        fillBottom();
        exitButton = new ExitButton(icon, theme);
        selectorContainer.getChildren().add(exitButton);
    }

    public void fillBottom(){
        hasBottom = true;
        Region fillRegion = new Region();
        VBox.setVgrow(fillRegion, Priority.ALWAYS);
        selectorContainer.getChildren().add(fillRegion);
    }

}
