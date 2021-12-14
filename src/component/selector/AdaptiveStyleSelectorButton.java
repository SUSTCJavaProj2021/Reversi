package component.selector;

import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class AdaptiveStyleSelectorButton extends Button {
    public static final double cornerRadii = 10;
    public static final Background defaultBackground = new Background(new BackgroundFill(Color.web("1D1F2C"), new CornerRadii(cornerRadii), null));
    public static final Background selectedBackground = new Background(new BackgroundFill(Color.web("2A2C38"), new CornerRadii(cornerRadii), null));
    public static final Background onMousePressedBackground = new Background(new BackgroundFill(Color.web("262834"), new CornerRadii(cornerRadii), null));
    public boolean isSelected;

    public enum State {
        DEFAULT, ONMOUSE, SELECTED;
    }

    public AdaptiveStyleSelectorButton(String text) {
        super(text);
        isSelected = false;
        setMinWidth(Button.USE_COMPUTED_SIZE);
        setMinHeight(Button.USE_COMPUTED_SIZE);
        setPrefSize(Double.POSITIVE_INFINITY, 45);
        setBackground(defaultBackground);
        setTextFill(Color.WHITE);
        setFont(new Font("Segoe UI", 14));

        //Unselected
        setOpacity(0.8);

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!isSelected){
                    setBackground(selectedBackground);
                    FadeTransition ft = new FadeTransition(Duration.millis(200), outer());
                    ft.setFromValue(0.8);
                    ft.setToValue(1.0);
                    ft.setCycleCount(1);
                    ft.play();
                }
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!isSelected) {
                    setBackground(defaultBackground);
                    FadeTransition ft = new FadeTransition(Duration.millis(200), outer());
                    ft.setFromValue(1.0);
                    ft.setToValue(0.8);
                    ft.setCycleCount(1);
                    ft.play();
                }
            }
        });

        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setBackground(onMousePressedBackground);
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setSelected();
            }
        });
    }

    public void setSelected(){
        isSelected = true;
        setBackground(selectedBackground);
        setOpacity(1.0);
    }

    public void setDeselected(){
        isSelected = false;
        setOpacity(0.8);
        FadeTransition ft = new FadeTransition(Duration.millis(200), outer());
        ft.setFromValue(1.0);
        ft.setToValue(0.8);
        ft.setCycleCount(1);
        ft.play();
        setBackground(defaultBackground);
    }

    private AdaptiveStyleSelectorButton outer() {
        return this;
    }

}
