package agh.ics.oop.model.mapElements;

import agh.ics.oop.SimulationConfig;
import agh.ics.oop.model.maps.WorldMap;
import agh.ics.oop.model.stats.AnimalStatistics;
import agh.ics.oop.model.stats.AnimalStatisticsBuilder;
import agh.ics.oop.model.utils.Direction;
import agh.ics.oop.model.utils.Genotype;

import agh.ics.oop.model.utils.Vector2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;


public class Animal implements MapElement {
    private Vector2d position;
    private int energy;
    private final Genotype genotype;
    private final Direction direction;

    private final int dailyEnergyCost;
    private final int reproductionCost;
    private final int energyFromPlant;
    private final LinkedList<Animal> children = new LinkedList<>();
    private int dayAlive = 0;
    private int deathDay;
    private int eatenPlants = 0;

    public Animal(Vector2d position, SimulationConfig config) {
        this.direction = new Direction();
        this.position = position;
        this.genotype = new Genotype(config.getGenotypeLength());
        this.energy = config.getStartingEnergy();
        this.dailyEnergyCost = config.getDailyEnergyCost();
        this.reproductionCost=config.getReproductionCost();
        this.energyFromPlant = config.getEnergyFromPlant();
    }

    public Animal(Vector2d position, SimulationConfig config, Animal strongerParent, Animal weakerParent) {
        this.direction = new Direction();
        this.position = position;
        this.energy = 2*config.getReproductionCost();
        this.genotype = new Genotype(config.getGenotypeLength(), strongerParent, weakerParent, config.getMinMutations(), config.getMaxMutations(), config.getMutationVariant());
        this.dailyEnergyCost = config.getDailyEnergyCost();
        this.reproductionCost=config.getReproductionCost();
        this.energyFromPlant = config.getEnergyFromPlant();
    }

    public void move(Vector2d rightBottomCorner) {
        this.direction.rotate(genotype.getCurrentGene());
        genotype.goToNextGene();
        Vector2d destination = this.position.add(this.direction.toVector());
        this.position = calculatePosition(destination, rightBottomCorner);
        this.energy-=this.dailyEnergyCost;
        this.dayAlive+=1;
    }

    public Vector2d getPosition() {
        return position;
    }

    @Override
    public void drawOnMap(GraphicsContext gc, double fieldWidth, double fieldHeight) {
        gc.setFill(Color.RED);
        gc.fillRect(position.getX() * fieldWidth, position.getY() * fieldHeight, fieldWidth, fieldHeight);
    }
    public void reproduction(Animal child) {
        this.energy-=reproductionCost;
        this.children.add(child);
    }
    public void consumePlant() {
        this.energy+=energyFromPlant;
        this.eatenPlants+=1;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public int getEnergy() {
        return energy;
    }
    public void addEnergy(int amount) {
        this.energy+=amount;
    }
    public Genotype getGenotype() {
        return genotype;
    }

    private Vector2d calculatePosition(Vector2d destination, Vector2d rightBottomCorner) {
        int x = destination.getX();
        int y = destination.getY();
        if (y > rightBottomCorner.getY()) {
            this.direction.reverse();
            y = rightBottomCorner.getY();
        } else if (y < 0) {
            this.direction.reverse();
            y = 0;
        }
        if (x > rightBottomCorner.getX()) x = 0;
        if (x < 0) x = rightBottomCorner.getX();
        return new Vector2d(x, y);
    }

    public void kill(int dayCounter) {
        this.deathDay = dayCounter;
    }
    public int getDescendants() {
        Set<Animal> animals = new HashSet<>(children);
        int amount = animals.size();
        for(Animal child : animals) {
                amount += child.getDescendants();
        }
        return amount;
    }
    public AnimalStatistics getAnimalStats()  {
        return new AnimalStatisticsBuilder().setGenotype(genotype).setCurrentActiveGene(genotype.getCurrentGene())
                .setEnergy(energy).setEatenPlants(eatenPlants).setChildrenAmount(children.size()).setDescendantAmount(getDescendants())
                .setDayAlive(dayAlive).setDeathDay(deathDay).build();
    }
}
