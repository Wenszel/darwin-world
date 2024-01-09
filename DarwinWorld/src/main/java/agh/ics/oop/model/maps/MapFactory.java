package agh.ics.oop.model.maps;

import agh.ics.oop.SimulationConfig;

public class MapFactory {
    public static WorldMap createMap(SimulationConfig config) {
        return switch (config.getMapType()) {
            case TUNNEL -> new TunnelMap(config);
            case GLOBE -> new GlobeMap(config);
        };
    }
}
