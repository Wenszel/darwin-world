package agh.ics.oop.model.maps;

import agh.ics.oop.model.mapElements.MapElement;
import agh.ics.oop.model.mapElements.MapField;
import agh.ics.oop.model.utils.Genotype;
import agh.ics.oop.model.utils.KeyValue;
import agh.ics.oop.model.utils.Vector2d;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface WorldMap {
    void initializeMap();
    void moveAnimals();
    Map<Vector2d, MapField> getMapFields();
    List<MapElement> stackObjectsToDraw(Vector2d position);
    int getWidth();
    int getHeight();
    UUID getId();
    void reproduceAnimals();
    double removeDeadAnimals(int dayCounter);
    void consumePlants();
    void growPlants(int amount);
    int getNumberOfAnimals();
    void unmarkAllAnimalsAsDescendants();
    List<KeyValue<Genotype, Integer>> getMostPopularGenotypes();
}
