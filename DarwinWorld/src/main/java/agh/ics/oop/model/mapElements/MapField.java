package agh.ics.oop.model.mapElements;

import agh.ics.oop.SimulationConfig;
import agh.ics.oop.model.utils.AnimalComparator;
import agh.ics.oop.model.utils.Vector2d;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

public class MapField {
    private final SimulationConfig config;
    private LinkedList<Animal> animalsOnField = new LinkedList();
    private boolean isPreferred;
    private final Vector2d fieldPosition;

    private Plant plant;

    public MapField(Vector2d pos, SimulationConfig config, boolean isPreferred) {
        this.fieldPosition = pos;
        this.config = config;
        this.isPreferred = isPreferred;
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

    public boolean isPreferred() {
        return isPreferred;
    }

    public void consumePlant() {

    }

    public Animal reproduceAnimals() {
        if(animalsOnField.size() >= 2) {
            List<Animal> parents = findParents();
            if(parents.get(1).getEnergy() >= config.getMinReproductionEnergy()) {
                Animal children = new Animal(this.fieldPosition, config,parents.get(0), parents.get(1));
                parents.get(0).addEnergy(-config.getReproductionCost());
                parents.get(1).addEnergy(-config.getReproductionCost());
                animalsOnField.add(children);
                return children;
            }
        }
        return null;
    }

    private List<Animal> findParents() {
        Animal strongerAnimal = animalsOnField.get(0);
        Animal weakerAnimal = animalsOnField.get(1);
        for(Animal animal : animalsOnField) {
            if(AnimalComparator.compare(animal,strongerAnimal) > 0) {
                weakerAnimal = strongerAnimal;
                strongerAnimal = animal;
            } else if (AnimalComparator.compare(animal, weakerAnimal) > 0) {
                weakerAnimal = animal;
            }
        }
        return new LinkedList<>(List.of(strongerAnimal, weakerAnimal));
    }
}
