package agh.ics.oop;

import agh.ics.oop.model.SimulationListener;
import agh.ics.oop.model.factories.MapFactory;
import agh.ics.oop.model.maps.WorldMap;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {

    private final List<SimulationListener>  listeners = new ArrayList<>();
    private final WorldMap map;
    private final SimulationConfig config;
    public Simulation(SimulationConfig config) {
        this.config = config;
        this.map = MapFactory.createMap(config);
    }
    @Override
    public void run() {
        map.initializeMap();
        while(true) {
            runDay();
            mapChanged("Day ended");
            try {
                Thread.sleep(100);
            } catch(InterruptedException err) {}
        }
    }
    public void runDay() {
        map.removeDeadAnimals();
        map.moveAnimals();
        map.consumePlants();
        map.reproduceAnimals();
        map.growPlants(config.getDailyPlantsGrowth());
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
