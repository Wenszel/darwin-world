package agh.ics.oop.model.maps.generators;

import agh.ics.oop.model.elements.MapField;
import agh.ics.oop.model.elements.Tunnel;
import agh.ics.oop.model.utils.Vector2d;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class TunnelGenerator {

    public static void generate(int numberOfTunnels, Map<Vector2d, MapField> mapFields, List<Tunnel> tunnels) {
        List<Vector2d> possibleLocations = new LinkedList<>(mapFields.keySet().stream().toList());
        for (int i = 0; i < numberOfTunnels; i++) {
            if (possibleLocations.size() < 2) return;
            Vector2d firstEntrance = drawLocation(possibleLocations);
            Vector2d secondEntrance = drawLocation(possibleLocations);
            Tunnel firstTunnel = new Tunnel(firstEntrance, secondEntrance);
            tunnels.add(firstTunnel);
            Tunnel secondTunnel = new Tunnel(secondEntrance, firstEntrance);
            tunnels.add(secondTunnel);
        }
    }

    static private Vector2d drawLocation(List<Vector2d> possibleLocations) {
        Random generator = new Random();
        int positionIndex = generator.nextInt(possibleLocations.size());
        Vector2d location = possibleLocations.get(positionIndex);
        possibleLocations.remove(positionIndex);
        return location;
    }
}
