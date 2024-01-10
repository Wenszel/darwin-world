package agh.ics.oop.model.mapElements;

import agh.ics.oop.model.utils.Vector2d;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;

public class Tunnel implements MapElement {
    Vector2d position;
    Vector2d exit;

    public Tunnel(Vector2d position, Vector2d exit) {
        this.position = position;
        this.exit = exit;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public Vector2d getExit() {
        return exit;
    }

    @Override
    public Shape getVisualRepresentation(double width, double height) {
        Shape mapRepresentation = new Rectangle(width, height);
        mapRepresentation.setStroke(Color.BROWN);
        mapRepresentation.setStrokeWidth(width/6);
        mapRepresentation.setStrokeType(StrokeType.INSIDE);
        mapRepresentation.setFill(Color.TRANSPARENT);
        return mapRepresentation;
    }
}
