package agh.ics.oop.model.mapElements;

import agh.ics.oop.SimulationConfig;
import agh.ics.oop.model.utils.AnimalComparator;
import agh.ics.oop.model.utils.Vector2d;

import java.util.*;
import java.util.stream.Collectors;

public class MapField {
    private final SimulationConfig config;
    private LinkedList<Animal> animalsOnField = new LinkedList();
    private boolean isPreferred;
    private final Vector2d fieldPosition;

    private Plant plant;
    private boolean hasPlant = false;

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
    public void growPlant() {
        this.plant = new Plant(fieldPosition);
        this.hasPlant=true;
    }
    public Optional<Animal> reproduceAnimals() {
        if(animalsOnField.size() >= 2) {
            List<Animal> parents = findParents();
            if(parents.get(1).getEnergy() >= config.getMinReproductionEnergy()) {
                Animal children = new Animal(this.fieldPosition, config,parents.get(0), parents.get(1));
                parents.get(0).addEnergy(-config.getReproductionCost());
                parents.get(1).addEnergy(-config.getReproductionCost());
                animalsOnField.add(children);
                return Optional.of(children);
            }
        }

        return Optional.empty();
    }

    private List<Animal> findParents() {
        List<Animal> twoStrongestAnimals = animalsOnField.stream()
                .sorted(new AnimalComparator())
                .limit(2)
                .collect(Collectors.toList());
        return twoStrongestAnimals;
    }

    public boolean getHasPlant() {
        return hasPlant;
    }
}
