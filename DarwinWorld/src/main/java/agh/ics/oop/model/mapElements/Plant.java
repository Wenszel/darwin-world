package agh.ics.oop.model.mapElements;

import agh.ics.oop.model.utils.Vector2d;
import javafx.scene.paint.Color;

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
    public VisualRepresentation getVisualRepresentation() {
        return new VisualRepresentationBuilder()
                .setBackground(Color.GREEN)
                .build();
    }
}
