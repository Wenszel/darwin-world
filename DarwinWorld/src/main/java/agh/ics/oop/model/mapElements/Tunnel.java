package agh.ics.oop.model.mapElements;

import agh.ics.oop.model.utils.Vector2d;
import javafx.scene.paint.Color;

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
    public Color getVisualRepresentation() {
        return Color.BROWN;
    }
}
