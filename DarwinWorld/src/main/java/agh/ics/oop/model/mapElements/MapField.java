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

    public List<MapElement> getElementsOnField() {
        List<MapElement> elements = new LinkedList<>(getAnimalsOnField());
        if (plant != null) {
            elements.add(plant);
        }
        return elements;
    }

    public boolean isPreferred() {
        return isPreferred;
    }

    public Optional<MapField> consumePlant() {
        Optional<Animal> strongestAnimal = animalsOnField.stream().max(new AnimalComparator());
        if(strongestAnimal.isPresent()) {
            Animal animal = strongestAnimal.get();
            animal.addEnergy(config.getEnergyFromPlant());
            this.hasPlant = false;
            this.plant = null;
            return Optional.of(this);
        }
        return Optional.empty();
    }
    public void growPlant() {
        this.hasPlant=true;
        this.plant = new Plant(fieldPosition);
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
        return animalsOnField.stream()
                .sorted(new AnimalComparator())
                .limit(2)
                .toList();
    }

    public boolean getHasPlant() {
        return hasPlant;
    }
}
