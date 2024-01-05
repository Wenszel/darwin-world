package agh.ics.oop.model.maps;

import agh.ics.oop.model.mapElements.Animal;
import agh.ics.oop.model.mapElements.MapElement;
import agh.ics.oop.model.mapElements.MapField;
import agh.ics.oop.model.mapElements.Tunnel;
import agh.ics.oop.model.maps.generators.TunnelGenerator;
import agh.ics.oop.model.utils.Vector2d;

import java.util.*;

public class TunnelMap extends GlobeMap implements WorldMap{
    private final List<Tunnel> tunnels = new ArrayList<>();
    public TunnelMap(int width, int height) {
       super(width, height);
    }

    @Override
    public void initializeMap() {
        //Tutaj będziemy wybierali preferowane pola, początkowe zwierzątka oraz rośliny itp
        super.initializeMap();
        TunnelGenerator.generate(10, mapFields, tunnels);
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
    public List<MapElement> objectsAt(Vector2d position) {
        List<MapElement> objectAtPosition = super.objectsAt(position);
        tunnels.forEach((tunnel) -> {
            if(tunnel.getPosition() == position) {
                objectAtPosition.add(tunnel);
            }
        });
        return objectAtPosition;
    }
}
