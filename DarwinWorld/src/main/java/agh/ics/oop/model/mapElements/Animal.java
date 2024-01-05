package agh.ics.oop.model.mapElements;

import agh.ics.oop.model.utils.Direction;
import agh.ics.oop.model.utils.Genotype;

import agh.ics.oop.model.utils.Vector2d;


public class Animal implements MapElement{
    private Vector2d position;
    private int energy;
    private final Genotype genotype;
    private Direction direction;
    public Animal(Vector2d position) {
        this.direction = new Direction();
        this.position = position;
        this.genotype = new Genotype(2);

    }
    public Animal(Vector2d position, Animal parent1, Animal parent2) {
        this.direction = new Direction();
        this.position = position;
        this.genotype = new Genotype(5, parent1, parent2, 0, 8);
    }

    public void move(){
        this.direction.rotate(genotype.getCurrentGene());
        this.position = this.position.add(this.direction.toVector());
    }

    public Vector2d getPosition() {
        return position;
    }

    public int getEnergy() {
        return energy;
    }

    public Genotype getGenotype() {
        return genotype;
    }
}
