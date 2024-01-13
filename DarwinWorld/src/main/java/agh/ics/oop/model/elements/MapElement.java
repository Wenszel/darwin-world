package agh.ics.oop.model.elements;

import agh.ics.oop.model.utils.Vector2d;
import javafx.scene.canvas.GraphicsContext;

public interface MapElement {
    Vector2d getPosition();
    void drawOnMap(GraphicsContext gc, double width, double height);
}
