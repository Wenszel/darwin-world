package agh.ics.oop.model.maps;

import agh.ics.oop.SimulationConfig;

public class MapFactory {
    public static WorldMap createMap(MapType mapType, SimulationConfig config) {
        return switch (mapType) {
            case TUNNEL -> new TunnelMap(config);
            case GLOBE -> new GlobeMap(config);
        };
    }
}
