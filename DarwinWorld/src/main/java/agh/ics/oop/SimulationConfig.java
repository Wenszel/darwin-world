package agh.ics.oop;

import agh.ics.oop.model.Config.Parameter;
import agh.ics.oop.model.maps.MapType;

import java.util.HashMap;

public class SimulationConfig {

    private final int mapWidth;
    private final int mapHeight;
    private final int genotypeLength;
    private final int  minReproductionEnergy;
    private final int reproductionCost;
    private final int  startingEnergy;
    private final int dailyEnergyCost;
    private final int dailyPlantsGrowth;
    private final int energyFromPlant;

    public SimulationConfig(HashMap<Parameter, Integer> params) {
        this.mapWidth = params.get(Parameter.MAP_WIDTH);
        this.mapHeight = params.get(Parameter.MAP_HEIGHT);
        this.genotypeLength = params.get(Parameter.GENOTYPE_LENGTH);
        this.minReproductionEnergy = params.get(Parameter.MIN_REPRODUCTION_ENERGY);
        this.reproductionCost = params.get(Parameter.REPRODUCTION_ENERGY_COST);
        this.startingEnergy = params.get(Parameter.STARTING_ENERGY);
        this.dailyEnergyCost = params.get(Parameter.DAILY_ENERGY_COST);
        this.dailyPlantsGrowth = params.get(Parameter.DAILY_PLANTS_GROWTH);
        this.energyFromPlant = params.get(Parameter.ENERGY_FROM_PLANT);
    }


    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getGenotypeLength() {
        return genotypeLength;
    }

    public int getMinReproductionEnergy() {
        return minReproductionEnergy;
    }

    public int getReproductionCost() {
        return reproductionCost;
    }

    public int getStartingEnergy() {
        return startingEnergy;
    }

    public int getDailyEnergyCost() {
        return dailyEnergyCost;
    }

    public int getDailyPlantsGrowth() {
        return dailyPlantsGrowth;
    }

    public int getEnergyFromPlant() {
        return energyFromPlant;
    }
}
