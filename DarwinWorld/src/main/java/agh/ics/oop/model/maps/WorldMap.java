package agh.ics.oop.model.maps;

import agh.ics.oop.model.mapElements.MapElement;
import agh.ics.oop.model.mapElements.MapField;
import agh.ics.oop.model.utils.Vector2d;

import java.util.List;
import java.util.Map;

public interface WorldMap {
    void initializeMap();
    void moveAnimals();
    Map<Vector2d, MapField> getMapFields();
    List<MapElement> stackObjectsToDraw(Vector2d position);
    int getWidth();
    int getHeight();
    void reproduceAnimals();
    void removeDeadAnimals(int dayCounter);
    void consumePlants();
    void growPlants(int amount);
}
