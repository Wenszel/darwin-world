package agh.ics.oop.model.stats;

import agh.ics.oop.model.utils.Genotype;
import agh.ics.oop.model.utils.KeyValue;

import java.util.List;

public class SimulationStatistics {
    private int currentDay;
    private int animalsAmount;
    private int plantsAmount;
    private int emptyFieldsAmount;
    private List<KeyValue<Genotype, Integer>> mostPopularGenotypes;
    private double averageEnergy;
    private double averageDeadAnimalsLifeLength;
    private double averageAnimalsChildrenAmount;

    public int getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(int currentDay) {
        this.currentDay = currentDay;
    }

    public int getAnimalsAmount() {
        return animalsAmount;
    }

    public void setAnimalsAmount(int animalsAmount) {
        this.animalsAmount = animalsAmount;
    }

    public int getPlantsAmount() {
        return plantsAmount;
    }

    public void setPlantsAmount(int plantsAmount) {
        this.plantsAmount = plantsAmount;
    }

    public int getEmptyFieldsAmount() {
        return emptyFieldsAmount;
    }

    public void setEmptyFieldsAmount(int emptyFieldsAmount) {
        this.emptyFieldsAmount = emptyFieldsAmount;
    }

    public List<KeyValue<Genotype, Integer>> getMostPopularGenotypes() {
        return mostPopularGenotypes;
    }

    public void setMostPopularGenotypes(List<KeyValue<Genotype, Integer>> mostPopularGenotypes) {
        this.mostPopularGenotypes = mostPopularGenotypes;
    }

    public double getAverageEnergy() {
        return averageEnergy;
    }

    public void setAverageEnergy(double averageEnergy) {
        this.averageEnergy = averageEnergy;
    }

    public double getAverageDeadAnimalsLifeLength() {
        return averageDeadAnimalsLifeLength;
    }

    public void setAverageDeadAnimalsLifeLength(double averageDeadAnimalsLifeLength) {
        this.averageDeadAnimalsLifeLength = averageDeadAnimalsLifeLength;
    }

    public double getAverageAnimalsChildrenAmount() {
        return averageAnimalsChildrenAmount;
    }
    public double setAverageAnimalsChildrenAmount(double averageAnimalsChildrenAmount) {
        return this.averageAnimalsChildrenAmount = averageAnimalsChildrenAmount;
    }
}
