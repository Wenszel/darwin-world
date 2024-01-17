package agh.ics.oop.model.maps;

import agh.ics.oop.model.config.Parameter;
import agh.ics.oop.model.mapElements.Animal;
import agh.ics.oop.model.mapElements.MapField;
import agh.ics.oop.model.utils.Vector2d;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import agh.ics.oop.model.config.SimulationConfig;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobeMapTest {

    private GlobeMap globeMap;
    private SimulationConfig config;
    private List<Animal> animals = new LinkedList<>();
    private List<MapField> emptyPreferredFields = new LinkedList<>();
    private List<MapField> emptyNormalFields = new LinkedList<>();

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        Map<Parameter, String> params = new HashMap<>();
        params.put(Parameter.MAP_WIDTH, "10");
        params.put(Parameter.MAP_HEIGHT, "10");
        params.put(Parameter.MAP_TYPE, "GLOBE");
        params.put(Parameter.MUTATION_TYPE, "SWAP");
        params.put(Parameter.GENOTYPE_LENGTH, "5");
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
        globeMap = new GlobeMap(config);
        globeMap.initializeMap();
        Field animalsField = GlobeMap.class.getDeclaredField("animals");
        Field emptyPreferredFieldsField = GlobeMap.class.getDeclaredField("emptyPreferredFields");
        Field emptyNormalFieldsField = GlobeMap.class.getDeclaredField("emptyNormalFields");
        animalsField.setAccessible(true);
        emptyPreferredFieldsField.setAccessible(true);
        emptyNormalFieldsField.setAccessible(true);
        animals = (List<Animal>) animalsField.get(globeMap);
        emptyPreferredFields = (List<MapField>) emptyPreferredFieldsField.get(globeMap);
        emptyNormalFields = (List<MapField>) emptyNormalFieldsField.get(globeMap);
    }
    @Test
    void givenConfig_whenInitializeMap_shouldGrowPlantsAndGenerateAnimals() {
        assertEquals(config.getMapHeight() * config.getMapWidth(), globeMap.getMapFields().size());
        assertEquals(config.getStartingPlants(), getNumberOfPlants());
        assertEquals(config.getStartingAnimals(), globeMap.getNumberOfAnimals());
    }

    private long getNumberOfPlants() {
        return globeMap.getMapFields().values().stream()
                .filter(MapField::getHasPlant)
                .count();
    }

    @Test
    void givenAnimals_whenMoveAnimals_shouldChangePositionOfAnimals() {
        List<Vector2d> positions = animals.stream()
                .map(Animal::getPosition)
                .toList();
        globeMap.moveAnimals();
        List<Vector2d> newPositions = animals.stream()
                .map(Animal::getPosition)
                .toList();
        for (int i = 0; i < positions.size(); i++) {
            assertNotEquals(positions.get(i), newPositions.get(i));
        }
    }

    @Test
    void givenTwoAnimalsOnTheSamePosition_whenReproduceAnimals_shouldCreateNewAnimal() {
        Animal firstAnimal = animals.get(0);
        Animal secondAnimal = animals.get(1);
        firstAnimal.setPosition(secondAnimal.getPosition());
        globeMap.mapFields.get(firstAnimal.getPosition()).addAnimal(firstAnimal);
        int numberOfAnimalsBefore = globeMap.getNumberOfAnimals();
        globeMap.reproduceAnimals();
        assertEquals(animals.size() - 1, numberOfAnimalsBefore);
    }

    @Test
    void givenAnimalAndPlantOnTheSamePosition_whenConsumePlant_shouldIncreaseEmptyFields() {
        Animal animal = animals.get(0);
        globeMap.mapFields.values().stream()
                .filter(MapField::getHasPlant)
                .findFirst()
                .ifPresent(mapField -> {
                    animal.setPosition(mapField.getPlant().getPosition());
                    mapField.addAnimal(animal);
        });
        int numberOfEmptyFieldsBefore = emptyNormalFields.size() + emptyPreferredFields.size();;
        globeMap.consumePlants();
        int numberOfEmptyFieldsAfter = emptyNormalFields.size() + emptyPreferredFields.size();
        assertTrue(numberOfEmptyFieldsAfter > numberOfEmptyFieldsBefore);
    }

    @Test
    void givenDeadAnimal_whenRemoveDeadAnimals_shouldRemoveAnimalFromAnimalsList() throws NoSuchFieldException, IllegalAccessException {
        Animal animal = animals.get(0);
        Field energyField = Animal.class.getDeclaredField("energy");
        energyField.setAccessible(true);
        energyField.set(animal, 0);
        globeMap.removeDeadAnimals(1);
        assertFalse(animals.contains(animal));
    }
}