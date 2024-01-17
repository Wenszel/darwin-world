package agh.ics.oop.model.factories;

import agh.ics.oop.model.config.SimulationConfig;
import agh.ics.oop.model.maps.GlobeMap;
import agh.ics.oop.model.maps.TunnelMap;
import agh.ics.oop.model.maps.WorldMap;

public class MapFactory {
    public static WorldMap createMap(SimulationConfig config) {
        return switch (config.getMapType()) {
            case TUNNEL -> new TunnelMap(config);
            case GLOBE -> new GlobeMap(config);
        };
    }
}
