package agh.ics.oop.model.mapElements;

import agh.ics.oop.model.utils.Vector2d;
import javafx.scene.canvas.GraphicsContext;
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
    public void drawOnMap(GraphicsContext gc, double fieldWidth, double fieldHeight){
        gc.setFill(Color.GREEN);
        gc.fillRect(position.getX() * fieldWidth, position.getY() * fieldHeight, fieldWidth, fieldHeight);
    }
}
