package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

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
    }

}
