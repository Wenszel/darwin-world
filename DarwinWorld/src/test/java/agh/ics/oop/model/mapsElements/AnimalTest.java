package agh.ics.oop.model.mapsElements;

import agh.ics.oop.SimulationConfig;
import agh.ics.oop.model.config.Parameter;
import agh.ics.oop.model.elements.Animal;
import agh.ics.oop.model.utils.Vector2d;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalTest {
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
    void givenTreeOfAnimals_whenGetDescendants_thenReturnCorrectNumber() {
        Animal parent1 = new Animal(new Vector2d(0, 0), config);
        Animal parent2 = new Animal(new Vector2d(0, 0), config);
        Animal child1 = new Animal(new Vector2d(0, 0), config, parent1, parent2);
        Animal child2 = new Animal(new Vector2d(0, 0), config, parent1, parent2);
        Animal child3 = new Animal(new Vector2d(0, 0), config, parent1, parent2);
        Animal childOfChild1 = new Animal(new Vector2d(0, 0), config, child1, child2);
        assertEquals(10, parent1.getDescendants());
    }

    @Test
    void GivenAnimalOnTheEdgeOfTheMap_whenMoveAnimal_shouldCorrectlyMove() {
        // Sometimes this test fails ??
        Animal animal = new Animal(new Vector2d(0, 0), config);
        animal.getGenotype().getGenotypeList().set(0, 6);
        System.out.println(animal.getGenotype().getCurrentGene());
        animal.move(new Vector2d(config.getMapWidth() - 1, config.getMapHeight() - 1));
        assertEquals(new Vector2d(9, 0), animal.getPosition());
    }
}
