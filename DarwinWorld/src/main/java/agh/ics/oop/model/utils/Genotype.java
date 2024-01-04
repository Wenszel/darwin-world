package agh.ics.oop.model.utils;


import agh.ics.oop.model.mapElements.Animal;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Genotype {
    private final List<Integer> genotype = new ArrayList<>();
    private int currentGeneIndex;
    private final int length;
    private int minMutations;
    private int maxMutations;
    public Genotype(int length) {
        this.length = length;
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            genotype.add(rand.nextInt(8));
        }
        currentGeneIndex = rand.nextInt(length);
    }

    public Genotype(int length, Animal parent1, Animal parent2, int minMutations, int maxMutations) {
        this.length = length;
        this.maxMutations = maxMutations;
        this.minMutations = minMutations;
        Random rand = new Random();
        currentGeneIndex = rand.nextInt(length);
        genotype.addAll(generateNewGenotype(parent1, parent2));
    }

    public int getCurrentGene() {
        int gene = genotype.get(currentGeneIndex);
        currentGeneIndex = (currentGeneIndex + 1) % length;
        return gene;
    }

    public List<Integer> getGenotypeList() {
        return genotype;
    }
    private List<Integer> generateNewGenotype(Animal parent1, Animal parent2) {
        int totalEnergy = parent1.getEnergy() + parent2.getEnergy();
        List<Integer> parent1GenotypeList = parent1.getGenotype().getGenotypeList();
        List<Integer> parent2GenotypeList = parent2.getGenotype().getGenotypeList();
        List<Integer> childrenGenotypeList = new ArrayList<>();
        int splitPoint = (int) Math.round(parent1GenotypeList.size() * ((double) parent1.getEnergy() / totalEnergy));
        Random rand = new Random();

        if (rand.nextBoolean()) {
            childrenGenotypeList.addAll(parent1GenotypeList.subList(0, splitPoint));
            childrenGenotypeList.addAll(parent2GenotypeList.subList(splitPoint, length));
        } else {
            childrenGenotypeList.addAll(parent2GenotypeList.subList(0, splitPoint));
            childrenGenotypeList.addAll(parent1GenotypeList.subList(splitPoint, length));
        }
        mutate(childrenGenotypeList);
        return childrenGenotypeList;
    }

    private void mutate(List<Integer> genotypeList) {
        Random rand = new Random();
        int mutationsAmount = rand.nextInt(maxMutations - minMutations + 1);
        for(int i=0; i<mutationsAmount; i++) {
            genotypeList.set(rand.nextInt(length), rand.nextInt(8));
        }
    }
}
