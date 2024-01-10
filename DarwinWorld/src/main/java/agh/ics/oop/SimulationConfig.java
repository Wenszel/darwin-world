package agh.ics.oop;

import agh.ics.oop.model.Config.Parameter;
import agh.ics.oop.model.Config.variants.MutationVariantName;
import agh.ics.oop.model.maps.MapType;

import java.util.HashMap;

public class SimulationConfig {

    private final int mapWidth;
    private final int mapHeight;
    private final MapType mapType;
    private final MutationVariantName mutationVariant;
    private final int genotypeLength;
    private final int minMutations;
    private final int maxMutations;
    private final int  minReproductionEnergy;
    private final int startingPlants;
    private final int startingAnimals;
    private final int reproductionCost;
    private final int  startingEnergy;
    private final int dailyEnergyCost;
    private final int dailyPlantsGrowth;
    private final int energyFromPlant;


    public SimulationConfig(HashMap<Parameter, String> params) {
        this.mapWidth =  Integer.parseInt(params.get(Parameter.MAP_WIDTH));
        this.mapHeight = Integer.parseInt(params.get(Parameter.MAP_HEIGHT));
        this.mapType = MapType.valueOf(params.get(Parameter.MAP_TYPE));
        this.mutationVariant = MutationVariantName.valueOf(params.get(Parameter.MUTATION_TYPE));
        this.genotypeLength = Integer.parseInt(params.get(Parameter.GENOTYPE_LENGTH));
        this.minMutations = Integer.parseInt(params.get(Parameter.MIN_MUTATIONS));
        this.maxMutations = Integer.parseInt(params.get(Parameter.MAX_MUTATIONS));
        this.startingPlants = Integer.parseInt(params.get(Parameter.STARTING_PLANTS));
        this.startingAnimals = Integer.parseInt(params.get(Parameter.STARTING_ANIMALS));
        this.minReproductionEnergy = Integer.parseInt(params.get(Parameter.MIN_REPRODUCTION_ENERGY));
        this.reproductionCost = Integer.parseInt(params.get(Parameter.REPRODUCTION_ENERGY_COST));
        this.startingEnergy = Integer.parseInt(params.get(Parameter.STARTING_ENERGY));
        this.dailyEnergyCost = Integer.parseInt(params.get(Parameter.DAILY_ENERGY_COST));
        this.dailyPlantsGrowth = Integer.parseInt(params.get(Parameter.DAILY_PLANTS_GROWTH));
        this.energyFromPlant = Integer.parseInt(params.get(Parameter.ENERGY_FROM_PLANT));
    }


    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public MapType getMapType() {
        return mapType;
    }

    public MutationVariantName getMutationVariant() {
        return mutationVariant;
    }

    public int getGenotypeLength() {
        return genotypeLength;
    }

    public int getMinMutations() {
        return minMutations;
    }

    public int getMaxMutations() {
        return maxMutations;
    }

    public int getMinReproductionEnergy() {
        return minReproductionEnergy;
    }

    public int getStartingPlants() {
        return startingPlants;
    }

    public int getStartingAnimals() {
        return startingAnimals;
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
