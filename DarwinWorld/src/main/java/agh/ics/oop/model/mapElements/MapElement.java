package agh.ics.oop.model.mapElements;

import agh.ics.oop.model.utils.Vector2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public interface MapElement {
    Vector2d getPosition();
    void drawOnMap(GraphicsContext gc, double width, double height);
}
