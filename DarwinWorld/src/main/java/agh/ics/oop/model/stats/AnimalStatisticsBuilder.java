package agh.ics.oop.model.stats;

import agh.ics.oop.model.Builder;
import agh.ics.oop.model.mapElements.Animal;
import agh.ics.oop.model.utils.Genotype;

import java.util.List;

public class AnimalStatisticsBuilder implements Builder<AnimalStatistics> {
    private Genotype genotype;
    private int currentActiveGene;
    private int energy;
    private int eatenPlants;
    private int childrenAmount;
    private int descendantAmount;
    private int dayAlive;
    private int deathDay;

    @Override
    public AnimalStatistics build() {
        AnimalStatistics stats = new AnimalStatistics();
        stats.setGenotype(this.genotype);
        stats.setCurrentActiveGene(this.currentActiveGene);
        stats.setEnergy(this.energy);
        stats.setEatenPlants(this.eatenPlants);
        stats.setChildrenAmount(this.childrenAmount);
        stats.setDescendantAmount(this.descendantAmount);
        stats.setDayAlive(this.dayAlive);
        stats.setDeathDay(this.deathDay);
        return stats;
    }

    public AnimalStatisticsBuilder setGenotype(Genotype genotype) {
        this.genotype = genotype;
        return this;
    }

    public AnimalStatisticsBuilder setCurrentActiveGene(int currentActiveGene) {
        this.currentActiveGene = currentActiveGene;
        return this;
    }

    public AnimalStatisticsBuilder setEnergy(int energy) {
        this.energy = energy;
        return this;
    }

    public AnimalStatisticsBuilder setEatenPlants(int eatenPlants) {
        this.eatenPlants = eatenPlants;
        return this;
    }

    public AnimalStatisticsBuilder setChildrenAmount(int childrenAmount) {
        this.childrenAmount = childrenAmount;
        return this;
    }

    public AnimalStatisticsBuilder setDescendantAmount(int descendantAmount) {
        this.descendantAmount = descendantAmount;
        return this;
    }

    public AnimalStatisticsBuilder setDayAlive(int dayAlive) {
        this.dayAlive = dayAlive;
        return this;
    }

    public AnimalStatisticsBuilder setDeathDay(int deathDay) {
        this.deathDay = deathDay;
        return this;
    }
}
