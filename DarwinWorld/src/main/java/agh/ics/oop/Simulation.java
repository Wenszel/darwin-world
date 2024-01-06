package agh.ics.oop;

import agh.ics.oop.model.SimulationListener;
import agh.ics.oop.model.mapElements.Animal;
import agh.ics.oop.model.maps.GlobeMap;
import agh.ics.oop.model.maps.MapFactory;
import agh.ics.oop.model.maps.MapType;
import agh.ics.oop.model.maps.WorldMap;
import agh.ics.oop.model.utils.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {

    private final List<SimulationListener>  listeners = new ArrayList<>();
    private final WorldMap map;
    public Simulation(MapType mapType) {
        map = MapFactory.createMap(mapType, 10, 10);
    }
    @Override
    public void run() {
        map.initializeMap();
        while(true) {
            runDay();
            mapChanged("Day ended");
            try {
                Thread.sleep(2000);
            } catch(InterruptedException err) {}
        }
    }
    public void runDay() {
        map.moveAnimals();
        map.reproduceAnimals();
    }

    public void addSubscriber(SimulationListener listener) {
        listeners.add(listener);
    }

    public void removeSubscriber(SimulationListener listener) {
        listeners.remove(listener);
    }
    private void mapChanged(String message) {
        for(SimulationListener listener : listeners) {
            listener.refreshSimulation(this, message);
        }
    }

    public WorldMap getMap() {
        return map;
    }

}
