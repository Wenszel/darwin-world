package agh.ics.oop.model.maps;

import agh.ics.oop.model.config.Parameter;
import agh.ics.oop.model.config.SimulationConfig;
import agh.ics.oop.model.mapElements.Animal;
import agh.ics.oop.model.mapElements.Tunnel;
import agh.ics.oop.model.utils.Genotype;
import agh.ics.oop.model.utils.Vector2d;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TunnelMapTest {
    private TunnelMap tunnelMap;
    private SimulationConfig config;
    private List<Animal> animals = new LinkedList<>();
    private List<Tunnel> tunnels = new ArrayList<>();

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
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
        tunnelMap = new TunnelMap(config);
        tunnelMap.initializeMap();
        Field animalsField = GlobeMap.class.getDeclaredField("animals");
        Field tunnelsField = TunnelMap.class.getDeclaredField("tunnels");
        animalsField.setAccessible(true);
        tunnelsField.setAccessible(true);
        animals = (List<Animal>) animalsField.get(tunnelMap);
        tunnels = (List<Tunnel>) tunnelsField.get(tunnelMap);
    }

    @Test
    void givenAnimalNextToTunnel_whenMoveAnimals_shouldCorrectlyMove() throws IllegalAccessException, NoSuchFieldException {
        Field genotypeField = Genotype.class.getDeclaredField("genotype");
        genotypeField.setAccessible(true);
        Field tunnelPositionField = Tunnel.class.getDeclaredField("position");
        tunnelPositionField.setAccessible(true);

        Animal animal = animals.get(0);
        tunnelPositionField.set(tunnels.get(0), new Vector2d(0, 0));

        animal.setPosition(new Vector2d(0, 1));
        animal.getGenotype().getGenotypeList().set(0, 4);
        tunnelMap.moveAnimals();
        assertNotEquals(new Vector2d(0, 0), animal.getPosition());
    }


}
