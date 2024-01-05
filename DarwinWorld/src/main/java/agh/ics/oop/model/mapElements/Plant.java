package agh.ics.oop.model.mapElements;

import agh.ics.oop.model.utils.Vector2d;

public class Plant implements MapElement{
    private final Vector2d position;
    public Plant(Vector2d position) {
        this.position = position;
    }
    @Override
    public Vector2d getPosition() {
        return position;
    }
}
