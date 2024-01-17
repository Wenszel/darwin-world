package agh.ics.oop.model.maps;

import agh.ics.oop.model.config.SimulationConfig;
import agh.ics.oop.model.mapElements.Animal;
import agh.ics.oop.model.mapElements.MapElement;
import agh.ics.oop.model.mapElements.Tunnel;
import agh.ics.oop.model.maps.generators.TunnelGenerator;
import agh.ics.oop.model.utils.Vector2d;

import java.util.*;

public class TunnelMap extends GlobeMap {
    private final List<Tunnel> tunnels = new ArrayList<>();
    public TunnelMap(SimulationConfig config) {
       super(config);
    }

    @Override
    public void initializeMap() {
        super.initializeMap();
        int numberOfFields = width * height;
        int numberOfTunnels = numberOfFields / 20;
        TunnelGenerator.generate(numberOfTunnels, mapFields, tunnels);
    }

    @Override
    public void moveAnimals() {
        for(Animal animal : animals) {
            mapFields.get(animal.getPosition()).removeAnimal(animal);
            move(animal);
            mapFields.get(animal.getPosition()).addAnimal(animal);
        }
    }

    private void move(Animal animal) {
        animal.move(new Vector2d(width - 1, height - 1));
        Vector2d position = animal.getPosition();
        Optional<Tunnel> foundTunnel = tunnels.stream()
                .filter(tunnel -> tunnel.getPosition().equals(position))
                .findFirst();

        foundTunnel.ifPresent(tunnel -> {
            animal.setPosition(tunnel.getExit());
        });
    }

    @Override
    public List<MapElement> stackObjectsToDraw(Vector2d position) {
        List<MapElement> stackObjectsAtPosition = super.stackObjectsToDraw(position);
        for (Tunnel tunnel: tunnels) {
            if (tunnel.getPosition().equals(position)) {
                stackObjectsAtPosition.add(tunnel);
                break;
            }
        }
        return stackObjectsAtPosition;
    }
}
