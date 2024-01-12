package agh.ics.oop.model.stats;

import agh.ics.oop.model.Builder;
import agh.ics.oop.model.utils.Genotype;
import agh.ics.oop.model.utils.KeyValue;

import java.util.List;

public class SimulationStatisticsBuilder implements Builder<SimulationStatistics> {
    private int currentDay;
    private int animalsAmount;
    private int plantsAmount;
    private int emptyFieldsAmount;
    private List<KeyValue<Genotype, Integer>> mostPopularGenotypes;
    private double averageEnergy;
    private double averageDeadAnimalsLifeLength;
    private double averageAnimalsChildrenAmount;


    @Override
    public SimulationStatistics build() {
        SimulationStatistics stats = new SimulationStatistics();
        stats.setCurrentDay(this.currentDay);
        stats.setAnimalsAmount(this.animalsAmount);
        stats.setPlantsAmount(this.plantsAmount);
        stats.setEmptyFieldsAmount(this.emptyFieldsAmount);
        stats.setMostPopularGenotypes(this.mostPopularGenotypes);
        stats.setAverageEnergy(this.averageEnergy);
        stats.setAverageDeadAnimalsLifeLength(this.averageDeadAnimalsLifeLength);
        stats.setAverageAnimalsChildrenAmount(this.averageAnimalsChildrenAmount);
        return stats;
    }
    public SimulationStatisticsBuilder collectMapStatistics(MapStatisticsCollector mapStatisticsCollector) {
        setAnimalsAmount(mapStatisticsCollector.getAnimalsAmount());
        setPlantsAmount(mapStatisticsCollector.getPlantsAmount());
        setAverageEnergy(mapStatisticsCollector.getAverageEnergy());
        setAverageAnimalsChildrenAmount(mapStatisticsCollector.getAverageAnimalsChildrenAmount());
        setEmptyFieldsAmount(mapStatisticsCollector.getEmptyFieldsAmount());
        return this;
    }
    public SimulationStatisticsBuilder setCurrentDay(int currentDay) {
        this.currentDay = currentDay;
        return this;
    }
    public SimulationStatisticsBuilder setAnimalsAmount(int animalsAmount) {
        this.animalsAmount = animalsAmount;
        return this;
    }

    public SimulationStatisticsBuilder setPlantsAmount(int plantsAmount) {
        this.plantsAmount = plantsAmount;
        return this;
    }

    public SimulationStatisticsBuilder setEmptyFieldsAmount(int emptyFieldsAmount) {
        this.emptyFieldsAmount = emptyFieldsAmount;
        return this;
    }

    public SimulationStatisticsBuilder setMostPopularGenotypes(List<KeyValue<Genotype, Integer>> mostPopularGenotypes) {
        this.mostPopularGenotypes = mostPopularGenotypes;
        return this;
    }

    public SimulationStatisticsBuilder setAverageEnergy(double averageEnergy) {
        this.averageEnergy = averageEnergy;
        return this;
    }

    public SimulationStatisticsBuilder setAverageDeadAnimalsLifeLength(double averageDeadAnimalsLifeLength) {
        this.averageDeadAnimalsLifeLength = averageDeadAnimalsLifeLength;
        return this;
    }

    public SimulationStatisticsBuilder setAverageAnimalsChildrenAmount(double averageAnimalsChildrenAmount) {
        this.averageAnimalsChildrenAmount = averageAnimalsChildrenAmount;
        return this;
    }
}
