package agh.ics.oop.model.maps;

import agh.ics.oop.model.mapElements.Animal;
import agh.ics.oop.model.mapElements.MapField;
import agh.ics.oop.model.utils.Vector2d;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GlobeMap implements WorldMap {

    private final Map<Vector2d, MapField> mapFields = new HashMap<>();
    private final List<Animal> animals = new LinkedList();
    private final int width;
    private final int height;
    public GlobeMap(int width, int height){
        this.width = width;
        this.height = height;
        for(int i=0; i<width; i++) {
            for(int j=0; j<height; j++) {
                Vector2d pos = new Vector2d(i,j);
                mapFields.put(pos, new MapField(pos));
            }
        }
    }
    public void initializeMap() {
        //Tutaj będziemy wybierali preferowane pola, początkowe zwierzątka oraz rośliny itp
        generateAnimals();
    }

    private void generateAnimals(){
        //Losujemy mapfields i dajemy do nich zwierzaki (tutaj 2 przykładowe)
        Animal animal = new Animal(new Vector2d(3,3));
        Animal animal2 = new Animal(new Vector2d(3,3));

        mapFields.get(animal.getPosition()).addAnimal(animal);
        animals.add(animal);

        mapFields.get(animal2.getPosition()).addAnimal(animal2);
        animals.add(animal2);

    }
    public void moveAnimals() {
        for(Animal animal : animals) {
            mapFields.get(animal.getPosition()).removeAnimal(animal);
            animal.move(new Vector2d(width-1, height-1));
            mapFields.get(animal.getPosition()).addAnimal(animal);
        }
    }
    public void reproduceAnimals() {
        for(MapField field : mapFields.values()) {
            Animal newAnimal = field.reproduceAnimals();
            if(newAnimal != null) {
                animals.add(newAnimal);
            }
        }
    }
    public Map<Vector2d, MapField> getMapFields() {
        return mapFields;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
