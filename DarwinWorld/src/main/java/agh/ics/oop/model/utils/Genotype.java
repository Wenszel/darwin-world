package agh.ics.oop.model.utils;


import agh.ics.oop.model.mapElements.Animal;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Genotype {
    private final List<Integer> genotype = new LinkedList<>();
    private int currentGeneIndex;
    private final int length;
    public Genotype(int length) {
        this.length = length;
        Random rand = new Random();
        for(int i=0; i<length; i++) {
            genotype.add(rand.nextInt(8));
        }
        currentGeneIndex = rand.nextInt(length);
    }
    public Genotype(int length, Animal parent1, Animal parent2) {
        this.length = length;
    }

    public int getCurrentGene() {
        int gene =  genotype.get(currentGeneIndex);
        currentGeneIndex = (currentGeneIndex + 1) % length;
        return gene;
    }
}
