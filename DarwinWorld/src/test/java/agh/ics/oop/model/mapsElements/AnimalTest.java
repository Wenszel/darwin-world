package agh.ics.oop.model.mapsElements;

import agh.ics.oop.model.config.Parameter;
import agh.ics.oop.model.config.SimulationConfig;
import agh.ics.oop.model.mapElements.Animal;
import agh.ics.oop.model.utils.Vector2d;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalTest {
    private SimulationConfig config;
    private Field descendantsField;
    private final Vector2d rightBottomCorner = new Vector2d(9, 9);

    @BeforeEach
    void setUp() throws NoSuchFieldException {
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
        descendantsField = Animal.class.getDeclaredField("descendants");
        descendantsField.setAccessible(true);
    }
    @Test
    void givenTreeOfAnimals_whenMarkAsAlfaAfterReproduction_thenReturnCorrectNumber() throws IllegalAccessException {
        Animal parent1 = new Animal(new Vector2d(0, 0), config);
        Animal parent2 = new Animal(new Vector2d(0, 0), config);
        Animal child1 = new Animal(new Vector2d(0, 0), config, parent1, parent2);
        parent1.reproduction(child1);
        Animal child2 = new Animal(new Vector2d(0, 0), config, parent1, parent2);
        parent1.reproduction(child2);
        Animal child3 = new Animal(new Vector2d(0, 0), config, parent1, parent2);
        parent1.reproduction(child3);
        Animal childOfChild1 = new Animal(new Vector2d(0, 0), config, child1, child2);
        child1.reproduction(childOfChild1);

        parent1.markAsAlfa();
        parent1.markDescendant(parent1);

        int descendants = (int) descendantsField.get(parent1);
        assertEquals(4, descendants);
    }

    @Test
    void givenFamilyOfAnimals_whenMarkAsAlfaBeforeReproduction_thenReturnCorrectNumber() throws IllegalAccessException {
        Animal parent1 = new Animal(new Vector2d(0, 0), config);
        parent1.markAsAlfa();
        parent1.markDescendant(parent1);

        Animal parent2 = new Animal(new Vector2d(0, 0), config);
        Animal child1 = new Animal(new Vector2d(0, 0), config, parent1, parent2);
        parent1.reproduction(child1);
        Animal child2 = new Animal(new Vector2d(0, 0), config, parent1, parent2);
        parent1.reproduction(child2);
        Animal child3 = new Animal(new Vector2d(0, 0), config, parent1, parent2);
        parent1.reproduction(child3);
        Animal childOfChild1 = new Animal(new Vector2d(0, 0), config, child1, child2);
        child1.reproduction(childOfChild1);

        int descendants = (int) descendantsField.get(parent1);
        assertEquals(4, descendants);
    }

    @Test
    public void givenAnimalOnTheEdgeOfTheMap_whenMoveAnimal_shouldCorrectlyMoveBelowLowerBoundary() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Vector2d destination = new Vector2d(5, 10);
        Animal animal = new Animal(new Vector2d(5, 9), config);
        Method privateMethod = Animal.class.getDeclaredMethod("calculatePosition", Vector2d.class, Vector2d.class);
        privateMethod.setAccessible(true);
        Vector2d result = (Vector2d) privateMethod.invoke(animal, destination, rightBottomCorner);
        Vector2d expected = new Vector2d(5, 9);
        assertEquals(expected, result);
    }

    @Test
    public void givenAnimalOnTheEdgeOfTheMap_whenMoveAnimal_shouldCorrectlyMoveRightBoundary() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Vector2d destination = new Vector2d(10, 0);
        Animal animal = new Animal(new Vector2d(9, 0), config);
        Method privateMethod = Animal.class.getDeclaredMethod("calculatePosition", Vector2d.class, Vector2d.class);
        privateMethod.setAccessible(true);
        Vector2d result = (Vector2d) privateMethod.invoke(animal, destination, rightBottomCorner);
        Vector2d expected = new Vector2d(0, 0);
        assertEquals(expected, result);
    }

    @Test
    public void givenAnimalOnTheEdgeOfTheMap_whenMoveAnimal_shouldCorrectlyMoveLeftBoundary() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Vector2d destination = new Vector2d(-1, 0);
        Animal animal = new Animal(new Vector2d(0, 0), config);
        Method privateMethod = Animal.class.getDeclaredMethod("calculatePosition", Vector2d.class, Vector2d.class);
        privateMethod.setAccessible(true);
        Vector2d result = (Vector2d) privateMethod.invoke(animal, destination, rightBottomCorner);
        Vector2d expected = new Vector2d(9, 0);
        assertEquals(expected, result);
    }
}
