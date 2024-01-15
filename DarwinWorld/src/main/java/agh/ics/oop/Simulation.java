package agh.ics.oop;

import agh.ics.oop.model.SimulationListener;
import agh.ics.oop.model.factories.MapFactory;
import agh.ics.oop.model.maps.WorldMap;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {

    private final List<SimulationListener>  listeners = new ArrayList<>();
    private final WorldMap map;
    private int simulationSpeed = 100;
    private final DayManager dayManager;
    private volatile boolean paused = false;
    private final Object pauseLock = new Object();

    public Simulation(SimulationConfig config) {
        this.map = MapFactory.createMap(config);
        map.initializeMap();
        this.dayManager = new DayManager(this.map, config);
    }

    @Override
    public void run() {
        while (true) {
            // Explanation of this code:
            // https://stackoverflow.com/questions/2779484/why-must-wait-always-be-in-synchronized-block?noredirect=1&lq=1
            synchronized (pauseLock) {
                if (paused) {
                    try {
                        pauseLock.wait();
                    } catch (InterruptedException ex) {
                        break;
                    }
                }
            }
            dayManager.runDay();
            mapChanged("Day ended");
            try {
                Thread.sleep(simulationSpeed);
            } catch(InterruptedException err) {
                break;
            }
        }
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll();
        }
    }

    public void addSubscriber(SimulationListener listener) {
        listeners.add(listener);
    }

    public void removeSubscriber(SimulationListener listener) {
        listeners.remove(listener);
    }

    private void mapChanged(String message) {
        for(SimulationListener listener : listeners) {
            listener.refreshSimulation(message);
        }
    }

    public DayManager getDayManager() {
        return dayManager;
    }

    public WorldMap getMap() {
        return map;
    }

    public boolean isPaused() {
        return paused;
    }

    public void increaseSimulationSpeed() {
        simulationSpeed = Math.max(simulationSpeed - 10, 10);
    }
    public void decreaseSimulationSpeed() {
        simulationSpeed = simulationSpeed + 20;
    }
}
