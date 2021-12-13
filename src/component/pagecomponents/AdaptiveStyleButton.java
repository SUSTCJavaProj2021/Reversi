package component.pagecomponents;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class AdaptiveStyleButton extends Button {
    public static final double cornerRadii = 10;
    public static final Background defaultBackground = new Background(new BackgroundFill(Color.web("1D1F2C"), new CornerRadii(cornerRadii), null));
    public static final Background selectedBackground = new Background(new BackgroundFill(Color.web("2A2C38"), new CornerRadii(cornerRadii), null));
    public static final Background onMousePressedBackground = new Background(new BackgroundFill(Color.web("262834"), new CornerRadii(cornerRadii), null));
    public boolean isSelected;

    public enum State {
        DEFAULT, ONMOUSE, SELECTED;
    }

    public AdaptiveStyleButton(String text) {
        super(text);
        isSelected = false;

        setMinWidth(Button.USE_COMPUTED_SIZE);
        setMinHeight(Button.USE_COMPUTED_SIZE);
        setPrefSize(Double.POSITIVE_INFINITY, 45);
        setBackground(defaultBackground);
        setTextFill(Color.WHITE);
        setFont(new Font("Segoe UI", 14));

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setBackground(selectedBackground);
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!isSelected) {
                    setBackground(defaultBackground);
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
                    setBackground(selectedBackground);
            }
        });
    }

}
