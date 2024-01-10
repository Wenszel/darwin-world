package agh.ics.oop.model.utils;


import agh.ics.oop.model.Config.variants.MutationVariant;
import agh.ics.oop.model.Config.variants.MutationVariantName;
import agh.ics.oop.model.factories.MutationVariantFactory;
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

    public Genotype(int length, Animal strongerParent, Animal weakerParent, int minMutations, int maxMutations, MutationVariantName mutationVariantName) {
        this.length = length;
        this.maxMutations = maxMutations;
        this.minMutations = minMutations;
        MutationVariant mutationVariant = MutationVariantFactory.createMutationVariant(mutationVariantName);
        Random rand = new Random();
        currentGeneIndex = rand.nextInt(length);
        genotype.addAll(generateNewGenotype(strongerParent, weakerParent, mutationVariant));
    }

    public int getCurrentGene() {
        return genotype.get(currentGeneIndex);
    }
    public void goToNextGene() {
        this.currentGeneIndex = (currentGeneIndex + 1) % length;
    }

    public List<Integer> getGenotypeList() {
        return genotype;
    }
    private List<Integer> generateNewGenotype(Animal strongerParent, Animal weakerParent, MutationVariant mutationVariant) {
        int totalEnergy = strongerParent.getEnergy() + weakerParent.getEnergy();
        AnimalComparator comparator = new AnimalComparator();
        if(comparator.compare(strongerParent, weakerParent) < 1) {
            Animal temp = strongerParent;
            strongerParent = weakerParent;
            weakerParent = temp;
        }
        List<Integer> strongerParentGenotypeList = strongerParent.getGenotype().getGenotypeList();
        List<Integer> weakerParentGenotypeList = weakerParent.getGenotype().getGenotypeList();
        List<Integer> childrenGenotypeList = new ArrayList<>();
        int splitPoint = (int) Math.round(strongerParentGenotypeList.size() * ((double) strongerParent.getEnergy() / totalEnergy));
        Random rand = new Random();

        if (rand.nextBoolean()) {
            childrenGenotypeList.addAll(strongerParentGenotypeList.subList(0, splitPoint));
            childrenGenotypeList.addAll(weakerParentGenotypeList.subList(splitPoint, length));
        } else {
            childrenGenotypeList.addAll(weakerParentGenotypeList.subList(0, splitPoint));
            childrenGenotypeList.addAll(strongerParentGenotypeList.subList(splitPoint, length));
        }
        mutationVariant.mutate(childrenGenotypeList, minMutations, maxMutations);
        return childrenGenotypeList;
    }

    @Override
    public String toString() {
        return genotype.toString();
    }
}
