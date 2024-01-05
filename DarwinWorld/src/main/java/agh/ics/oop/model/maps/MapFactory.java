package agh.ics.oop.model.maps;

public class MapFactory {
    public static WorldMap createMap(MapType mapType, int width, int height) {
        return switch (mapType) {
            case TUNNEL -> new TunnelMap(width, height);
            case GLOBE -> new GlobeMap(width, height);
        };
    }
}
