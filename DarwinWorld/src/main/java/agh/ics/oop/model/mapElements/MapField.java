package agh.ics.oop.model.mapElements;

import agh.ics.oop.model.utils.AnimalComparator;
import agh.ics.oop.model.utils.Vector2d;

import java.util.*;
import java.util.stream.Collectors;

public class MapField {
    private LinkedList<Animal> animalsOnField = new LinkedList();
    private boolean isPreferred;
    private final Vector2d fieldPosition;
    private Plant plant;

    public MapField(Vector2d pos) {
        this.fieldPosition = pos;
    }

    public void addAnimal(Animal animal) {
        animalsOnField.add(animal);
    }

    public void removeAnimal(Animal animal) {
        animalsOnField.remove(animal);
    }

    public List<Animal> getAnimalsOnField() {
        return Collections.unmodifiableList(animalsOnField);
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

    public Optional<Animal> reproduceAnimals() {
        if(animalsOnField.size() >= 2) {
            List<Animal> parents = findParents();
            Animal children = new Animal(this.fieldPosition, parents.get(0), parents.get(1));
            animalsOnField.add(children);
            return Optional.of(children);
        }

        return Optional.empty();
    }

    private List<Animal> findParents() {
        return animalsOnField.stream()
                .sorted(new AnimalComparator())
                .limit(2)
                .toList();
    }
}
