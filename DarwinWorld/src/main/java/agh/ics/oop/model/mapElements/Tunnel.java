package agh.ics.oop.model.mapElements;

import agh.ics.oop.model.utils.Vector2d;
import javafx.scene.canvas.GraphicsContext;
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
    public void drawOnMap(GraphicsContext gc, double fieldWidth, double fieldHeight) {
        gc.setStroke(Color.BROWN);
        double strokeWidth = fieldWidth / 6;
        gc.setLineWidth(strokeWidth);
        gc.strokeRect(position.getX() * fieldWidth + strokeWidth / 2,
                position.getY() * fieldHeight + strokeWidth / 2,
                fieldWidth - strokeWidth,
                fieldHeight - strokeWidth);
    }
}
