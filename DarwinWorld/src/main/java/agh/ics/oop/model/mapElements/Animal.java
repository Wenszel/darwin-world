package agh.ics.oop.model.mapElements;

import agh.ics.oop.SimulationConfig;
import agh.ics.oop.model.maps.WorldMap;
import agh.ics.oop.model.utils.Direction;
import agh.ics.oop.model.utils.Genotype;

import agh.ics.oop.model.utils.Vector2d;
import javafx.scene.paint.Color;


public class Animal implements MapElement {
    private Vector2d position;
    private int energy;
    private final Genotype genotype;
    private final Direction direction;
    private final int dailyEnergyCost;

    public Animal(Vector2d position, SimulationConfig config) {
        this.direction = new Direction();
        this.position = position;
        this.genotype = new Genotype(config.getGenotypeLength());
        this.energy = config.getStartingEnergy();
        this.dailyEnergyCost = config.getDailyEnergyCost();
    }

    public Animal(Vector2d position, SimulationConfig config, Animal strongerParent, Animal weakerParent) {
        this.direction = new Direction();
        this.position = position;
        this.energy = 2*config.getReproductionCost();
        this.genotype = new Genotype(config.getGenotypeLength(), strongerParent, weakerParent, 0, 8);
        this.dailyEnergyCost = config.getDailyEnergyCost();
    }

    public void move(Vector2d rightBottomCorner) {
        this.direction.rotate(genotype.getCurrentGene());
        Vector2d destination = this.position.add(this.direction.toVector());
        this.position = calculatePosition(destination, rightBottomCorner);
        this.energy-=this.dailyEnergyCost;
    }

    public Vector2d getPosition() {
        return position;
    }

    @Override
    public VisualRepresentation getVisualRepresentation() {
        return new VisualRepresentationBuilder()
                .setBackground(Color.RED)
                .setText("A")
                .build();
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
}
