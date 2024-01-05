package agh.ics.oop.model.mapElements;

import agh.ics.oop.SimulationConfig;
import agh.ics.oop.model.maps.WorldMap;
import agh.ics.oop.model.utils.Direction;
import agh.ics.oop.model.utils.Genotype;

import agh.ics.oop.model.utils.Vector2d;


public class Animal implements MapElement {
    private Vector2d position;
    private int energy;
    private final Genotype genotype;
    private Direction direction;

    public Animal(Vector2d position, SimulationConfig config) {
        this.direction = new Direction();
        this.position = position;
        this.genotype = new Genotype(config.getGenotypeLength());
        this.energy = config.getStartingEnergy();

    }

    public Animal(Vector2d position, SimulationConfig config, Animal strongerParent, Animal weakerParent) {
        this.direction = new Direction();
        this.position = position;
        this.energy = 2*config.getReproductionCost();
        this.genotype = new Genotype(config.getGenotypeLength(), strongerParent, weakerParent, 0, 8);
    }


    public void move(Vector2d rightBottomCorner) {
        this.direction.rotate(genotype.getCurrentGene());
        Vector2d destination = this.position.add(this.direction.toVector());
        this.position = calculatePosition(destination, rightBottomCorner);
    }

    public Vector2d getPosition() {
        return position;
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
