package agh.ics.oop;

import agh.ics.oop.model.SimulationListener;
import agh.ics.oop.model.factories.MapFactory;
import agh.ics.oop.model.maps.WorldMap;
import agh.ics.oop.model.stats.SimulationStatistics;
import agh.ics.oop.model.stats.SimulationStatisticsBuilder;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {

    private final List<SimulationListener>  listeners = new ArrayList<>();
    private final WorldMap map;
    private final SimulationConfig config;
    private int dayCounter = 1;
    private double deadAnimalsAverageLifeLength;
    private volatile boolean paused = false;
    private final Object pauseLock = new Object();

    public Simulation(SimulationConfig config) {
        this.config = config;
        this.map = MapFactory.createMap(config);
        map.initializeMap();
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
            runDay();
            mapChanged("Day ended");
            try {
                Thread.sleep(100);
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

    public void runDay() {
        deadAnimalsAverageLifeLength = map.removeDeadAnimals(dayCounter);
        map.moveAnimals();
        map.consumePlants();
        map.reproduceAnimals();
        map.growPlants(config.getDailyPlantsGrowth());
        dayCounter+=1;
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

    public WorldMap getMap() {
        return map;
    }

    public int getDayCounter() {
        return dayCounter;
    }

    public double getDeadAnimalsAverageLifeLength() {
        return deadAnimalsAverageLifeLength;
    }
}
