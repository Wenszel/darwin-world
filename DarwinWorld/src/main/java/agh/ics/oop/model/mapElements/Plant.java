package agh.ics.oop.model.mapElements;

import agh.ics.oop.model.utils.Vector2d;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Plant implements MapElement{
    private final Vector2d position;
    public Plant(Vector2d position) {
        this.position = position;
    }
    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public Shape getVisualRepresentation(double width, double height){
        Shape mapRepresentation = new Rectangle(width, height);
        mapRepresentation.setFill(Color.GREEN);
        return mapRepresentation;
    }
}
