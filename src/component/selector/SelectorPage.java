package component.selector;

import controller.Log0j;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class SelectorPage {
    public GridPane root;
    public VBox selector;

    public ArrayList<SelectorButton> buttonList;

    public SelectorButton currentSelectedButton;
    public ExitButton exitButton;

    public SelectorPage(Label title) {
        root = new GridPane();
        {
            Separator separators[] = new Separator[2];
            for (int i = 0; i < 2; i++) {
                separators[i] = new Separator(Orientation.VERTICAL);
                separators[i].setOpacity(0);
                separators[i].setMinWidth(10);
            }
            root.add(separators[0], 0, 0);
            root.add(separators[1], 2, 0);
        }

        selector = new VBox();
        selector.setPrefWidth(SelectorButton.PREFERRED_WIDTH + 20);
        selector.setMinWidth(VBox.USE_COMPUTED_SIZE);
        GridPane.setVgrow(selector, Priority.ALWAYS);
        GridPane.setHgrow(selector, Priority.ALWAYS);

        if (title != null) {
            Separator separators[] = new Separator[2];
            for (int i = 0; i < 2; i++) {
                separators[i] = new Separator(Orientation.HORIZONTAL);
                separators[i].setMinHeight(15);
                separators[i].setMaxHeight(15);
                separators[i].setMinWidth(HBox.USE_COMPUTED_SIZE);
                separators[i].setOpacity(0);
            }
            selector.getChildren().addAll(separators[0], title, separators[1]);
        }
        root.add(selector, 1, 0);


        buttonList = new ArrayList<SelectorButton>(0);
        currentSelectedButton = null;

        root.setBackground(new Background(new BackgroundFill(Color.web("1D1F2C"), null, null)));
    }

    public void init() {
        setCurrentSelection(buttonList.get(0));
    }

    public void addSelection(String selectionText, Node node, ImageView icon) {
        SelectorButton button = new SelectorButton(selectionText, node, icon);
        if (buttonList.size() != 0) {
            Separator separator = new Separator(Orientation.HORIZONTAL);
            separator.setMinHeight(5);
            separator.setMaxHeight(5);
            separator.setMinWidth(HBox.USE_COMPUTED_SIZE);
            separator.setOpacity(0);
            selector.getChildren().add(separator);
        }
        buttonList.add(button);
        selector.getChildren().add(button);

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
        Log0j.writeLog("Switched selection: " + currentSelectedButton.textProperty().getValue());
    }


    public void setExitButton(ImageView icon) {
        Region fillRegion = new Region();
        VBox.setVgrow(fillRegion, Priority.ALWAYS);
        selector.getChildren().add(fillRegion);

        exitButton = new ExitButton(icon);
        selector.getChildren().add(exitButton);
    }

}
