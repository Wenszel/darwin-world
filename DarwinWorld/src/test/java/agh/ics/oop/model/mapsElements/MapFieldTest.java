package agh.ics.oop.model.mapsElements;

import agh.ics.oop.model.config.Parameter;
import agh.ics.oop.model.config.SimulationConfig;
import agh.ics.oop.model.mapElements.Animal;
import agh.ics.oop.model.mapElements.MapField;
import agh.ics.oop.model.utils.Vector2d;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class MapFieldTest {
    private SimulationConfig config;

    @BeforeEach
    void setUp(){
        Map<Parameter, String> params = new HashMap<>();
        params.put(Parameter.MAP_WIDTH, "10");
        params.put(Parameter.MAP_HEIGHT, "10");
        params.put(Parameter.MAP_TYPE, "GLOBE");
        params.put(Parameter.MUTATION_TYPE, "SWAP");
        params.put(Parameter.GENOTYPE_LENGTH, "1");
        params.put(Parameter.MIN_MUTATIONS, "1");
        params.put(Parameter.MAX_MUTATIONS, "3");
        params.put(Parameter.STARTING_ANIMALS, "5");
        params.put(Parameter.STARTING_PLANTS, "10");
        params.put(Parameter.MIN_REPRODUCTION_ENERGY, "10");
        params.put(Parameter.REPRODUCTION_ENERGY_COST, "5");
        params.put(Parameter.STARTING_ENERGY, "100");
        params.put(Parameter.DAILY_ENERGY_COST, "1");
        params.put(Parameter.DAILY_PLANTS_GROWTH, "1");
        params.put(Parameter.ENERGY_FROM_PLANT, "10");
        params.put(Parameter.SAVE_TO_CSV, "false");
        config = new SimulationConfig(params);
    }

    @Test
    public void givenMapFieldWith2Animals_whenReproduceAnimals_thenTwoStrongestAnimalsHaveChild() {
        MapField mapField = new MapField(new Vector2d(1, 1), config, false);
        Animal animal1 = new Animal(new Vector2d(1, 1), config);
        Animal animal2 = new Animal(new Vector2d(1, 1), config);
        mapField.addAnimal(animal1);
        mapField.addAnimal(animal2);
        mapField.reproduceAnimals();
        assert (animal1.getChildrenAmount() == 1);
        assert (animal2.getChildrenAmount() == 1);
    }
}
