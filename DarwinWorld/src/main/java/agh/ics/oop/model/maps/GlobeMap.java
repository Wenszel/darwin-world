package agh.ics.oop.model.maps;

import agh.ics.oop.model.mapElements.Animal;
import agh.ics.oop.model.mapElements.MapElement;
import agh.ics.oop.model.mapElements.MapField;
import agh.ics.oop.model.utils.Vector2d;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GlobeMap implements WorldMap {
    protected final Map<Vector2d, MapField> mapFields = new HashMap<>();
    protected final List<Animal> animals = new LinkedList<>();
    protected final int width;
    protected final int height;

    public GlobeMap(int width, int height) {
        this.width = width;
        this.height = height;
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                mapFields.put(new Vector2d(i,j), new MapField());
            }
        }
    }

    @Override
    public void initializeMap() {
        //Tutaj będziemy wybierali preferowane pola, początkowe zwierzątka oraz rośliny itp
        generateAnimals();
    }

    protected void generateAnimals(){
        Animal animal = new Animal(new Vector2d(3,3));
        Animal animal2 = new Animal(new Vector2d(5,7));
        mapFields.get(animal.getPosition()).addAnimal(animal);
        animals.add(animal);
        mapFields.get(animal2.getPosition()).addAnimal(animal2);
        animals.add(animal2);
    }

    @Override
    public void moveAnimals() {
        for(Animal animal : animals) {
            mapFields.get(animal.getPosition()).removeAnimal(animal);
            animal.move(new Vector2d(width-1, height-1));
            mapFields.get(animal.getPosition()).addAnimal(animal);
        }
    }

    @Override
    public Map<Vector2d, MapField> getMapFields() {
        return mapFields;
    }

    @Override
    public List<MapElement> objectsAt(Vector2d position) {
        return mapFields.get(position).getElementsOnField();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
