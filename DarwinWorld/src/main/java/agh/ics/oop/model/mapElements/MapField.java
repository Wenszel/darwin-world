package agh.ics.oop.model.mapElements;

import agh.ics.oop.model.utils.AnimalComparator;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

public class MapField {
    private TreeSet<Animal> animalsOnField = new TreeSet<>(new AnimalComparator());
    private boolean isPreferred;
    private Plant plant;

    public void addAnimal(Animal animal){
        animalsOnField.add(animal);
    }
    public void removeAnimal(Animal animal) {animalsOnField.remove(animal);}


    public List<Animal> getAnimalsOnField() {
        return new LinkedList<>(animalsOnField);
    }

    public List<MapElement> getElementsOnField() {
        List<MapElement> elements = new LinkedList<>(getAnimalsOnField());
        if (plant != null) {
            elements.add(plant);
        }
        return elements;
    }

    public void consumePlant() {

    }
    public void reproduceAnimals() {
        //możemy zwracać listę powstałych zwierzątek zamiast void
    }
}
