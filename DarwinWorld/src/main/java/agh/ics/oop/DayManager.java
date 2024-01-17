package agh.ics.oop;

import agh.ics.oop.model.config.SimulationConfig;
import agh.ics.oop.model.maps.WorldMap;

public class DayManager {
    private int day;
    private WorldMap map;
    private double deadAnimalsAverageLifeLength;
    private int population;
    private SimulationConfig simulationConfig;

    public DayManager(WorldMap map, SimulationConfig simulationConfig) {
        this.map = map;
        this.simulationConfig = simulationConfig;
    }

    public void runDay() {
        deadAnimalsAverageLifeLength = map.removeDeadAnimals(day);
        map.moveAnimals();
        population = map.getNumberOfAnimals();
        map.consumePlants();
        map.reproduceAnimals();
        map.growPlants(simulationConfig.getDailyPlantsGrowth());
        day += 1;
    }

    public int getDay() {
        return day;
    }

    public double getDeadAnimalsAverageLifeLength() {
        return deadAnimalsAverageLifeLength;
    }

}
