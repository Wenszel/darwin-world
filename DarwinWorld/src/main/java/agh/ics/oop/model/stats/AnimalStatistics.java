package agh.ics.oop.model.stats;

import agh.ics.oop.model.utils.Genotype;

public class AnimalStatistics {
    private Genotype genotype;
    private int currentActiveGene;
    private int energy;
    private int eatenPlants;
    private int childrenAmount;
    private int descendantAmount;
    private int dayAlive;
    private int deathDay;

    public Genotype getGenotype() {
        return genotype;
    }

    public void setGenotype(Genotype genotype) {
        this.genotype = genotype;
    }

    public int getCurrentActiveGene() {
        return currentActiveGene;
    }

    public void setCurrentActiveGene(int currentActiveGene) {
        this.currentActiveGene = currentActiveGene;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getEatenPlants() {
        return eatenPlants;
    }

    public void setEatenPlants(int eatenPlants) {
        this.eatenPlants = eatenPlants;
    }

    public int getChildrenAmount() {
        return childrenAmount;
    }

    public void setChildrenAmount(int childrenAmount) {
        this.childrenAmount = childrenAmount;
    }

    public int getDescendantAmount() {
        return descendantAmount;
    }

    public void setDescendantAmount(int descendantAmount) {
        this.descendantAmount = descendantAmount;
    }

    public int getDayAlive() {
        return dayAlive;
    }

    public void setDayAlive(int dayAlive) {
        this.dayAlive = dayAlive;
    }

    public int getDeathDay() {
        return deathDay;
    }

    public void setDeathDay(int deathDay) {
        this.deathDay = deathDay;
    }
}
