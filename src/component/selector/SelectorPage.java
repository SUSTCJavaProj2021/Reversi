package component.selector;

import controller.logger.Log0j;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import view.Theme;

import java.util.ArrayList;

public class SelectorPage {
    public static final double CORNER_RADII = 10;

    public GridPane root;
    public VBox selector;

    public ArrayList<SelectorButton> buttonList;

    public SelectorButton currentSelectedButton;
    public ExitButton exitButton;

    public boolean hasBottom;

    public Theme theme;


    public SelectorPage(Theme theme){
        this.theme = theme;
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
        selector.setPrefWidth(SelectorButton.PREFERRED_WIDTH);
        selector.setMinWidth(VBox.USE_COMPUTED_SIZE);
        GridPane.setVgrow(selector, Priority.ALWAYS);
        GridPane.setHgrow(selector, Priority.ALWAYS);

        root.add(selector, 1, 0);


        buttonList = new ArrayList<SelectorButton>(0);
        currentSelectedButton = null;
        hasBottom = false;

        root.setPrefWidth(SelectorButton.PREFERRED_WIDTH + 20);
        root.setBackground(new Background(new BackgroundFill(Color.rgb(0,0,0,0.05), new CornerRadii(CORNER_RADII), null)));
    }

    public SelectorPage(Label title, Theme theme) {
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
            selector.getChildren().addAll(separators[0], title, separators[1]);
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
            selector.getChildren().add(separator);
        }
        buttonList.add(button);
        selector.getChildren().add(button);

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
        selector.getChildren().add(exitButton);
    }

    public void fillBottom(){
        hasBottom = true;
        Region fillRegion = new Region();
        VBox.setVgrow(fillRegion, Priority.ALWAYS);
        selector.getChildren().add(fillRegion);
    }

}
