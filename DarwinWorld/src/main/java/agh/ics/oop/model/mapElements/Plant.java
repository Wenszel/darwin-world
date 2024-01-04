package agh.ics.oop.model.mapElements;

import agh.ics.oop.model.utils.Vector2d;

public class Plant implements MapElement{
    private Vector2d position;
    @Override
    public Vector2d getPosition() {
        return position;
    }
}
