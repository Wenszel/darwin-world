package agh.ics.oop.model.mapElements;

import agh.ics.oop.model.utils.Vector2d;
import javafx.scene.paint.Color;

public interface MapElement {
    Vector2d getPosition();
    VisualRepresentation getVisualRepresentation();
}
