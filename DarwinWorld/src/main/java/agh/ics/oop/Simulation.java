package agh.ics.oop;

import agh.ics.oop.model.SimulationListener;
import agh.ics.oop.model.mapElements.Animal;
import agh.ics.oop.model.maps.GlobeMap;
import agh.ics.oop.model.maps.WorldMap;
import agh.ics.oop.model.utils.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {

    private final List<SimulationListener>  listeners = new ArrayList<>();
    private final GlobeMap map;
    public Simulation(GlobeMap map) {
        this.map = map;
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

    public GlobeMap getMap() {
        return map;
    }

}
