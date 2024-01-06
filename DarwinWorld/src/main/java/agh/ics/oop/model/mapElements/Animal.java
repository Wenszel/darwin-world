package agh.ics.oop.model.mapElements;

import javafx.scene.paint.Color;
import agh.ics.oop.model.utils.Direction;
import agh.ics.oop.model.utils.Genotype;

import agh.ics.oop.model.utils.Vector2d;


public class Animal implements MapElement {
    private Vector2d position;
    private int energy;
    private final Genotype genotype;
    private final Direction direction;

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


    public void move(Vector2d rightBottomCorner) {
        this.direction.rotate(genotype.getCurrentGene());
        Vector2d destination = this.position.add(this.direction.toVector());
        this.position = calculatePosition(destination, rightBottomCorner);
    }

    public Vector2d getPosition() {
        return position;
    }

    @Override
    public VisualRepresentation getVisualRepresentation() {
        return new VisualRepresentationBuilder()
                .setBackground(Color.GRAY)
                .setText("A")
                .build();
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public int getEnergy() {
        return energy;
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
