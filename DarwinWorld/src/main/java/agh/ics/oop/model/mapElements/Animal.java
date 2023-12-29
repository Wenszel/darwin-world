package agh.ics.oop.model.mapElements;

import agh.ics.oop.model.utils.Genotype;
import agh.ics.oop.model.utils.Vector2d;

import java.util.List;

public class Animal {
    private Vector2d position;
    private final Genotype genotype;
    public Animal(Vector2d position, List genotype) {
        this.position = position;
        this.genotype = new Genotype(genotype);
    }

    public void move(){
        this.position = new Vector2d(position.getX()+1, position.getY()+1);
    }

    public Vector2d getPosition() {
        return position;
    }
}
