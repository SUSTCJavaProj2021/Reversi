package model;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

/**
 * This Chess shall support three modes: Transparent Mode, Black Mode, White Mode.
 *
 */
public class Chess extends Circle {

    private Color color;

    public Chess(double radius, Color color) {
        super(radius, color);
    }

    public Chess(double radius) {
        super(radius, Color.TRANSPARENT);
    }

    public void setColor(Color color) {
        super.setFill(color);
        this.color = color;
        if (!color.equals(Color.TRANSPARENT)) {
            DropShadow dropShadow = new DropShadow();
            dropShadow.setRadius(radiusProperty().doubleValue() + 5);
            dropShadow.setOffsetX(3.0);
            dropShadow.setOffsetY(3.0);
            dropShadow.setColor(Color.rgb(0, 0, 0, 0.45));
            setEffect(dropShadow);
        } else {
            setEffect(null);
        }
    }

}
