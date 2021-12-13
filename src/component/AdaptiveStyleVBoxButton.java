package component;

import javafx.animation.KeyValue;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class AdaptiveStyleVBoxButton extends Button {
    public final Background defaultBackground;
    public final Background alteredBackground;

    public AdaptiveStyleVBoxButton(String text){
        super(text);
        defaultBackground = new Background(new BackgroundFill(Color.web("242424"), null, null));
        alteredBackground = new Background(new BackgroundFill(Color.WHITE, null, null));

        setMinWidth(VBox.USE_COMPUTED_SIZE);
        setMinHeight(VBox.USE_COMPUTED_SIZE);
        setPrefSize(Double.POSITIVE_INFINITY, 60);
        setBackground(defaultBackground);
        setTextFill(Color.WHITE);
        setFont(new Font("Segoe UI", 14));

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setBackground(alteredBackground);
                setTextFill(Color.BLACK);
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setBackground(defaultBackground);
                setTextFill(Color.WHITE);
            }
        });
    }
}
