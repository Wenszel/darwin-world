package agh.ics.oop;

import agh.ics.oop.model.SimulationListener;
import agh.ics.oop.model.mapElements.Animal;
import agh.ics.oop.model.utils.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {

    private List<Animal> animals;
    private final List<SimulationListener>  listeners = new ArrayList<>();
    public Simulation() {
        this.animals = new ArrayList<>();
        this.animals.add(new Animal(new Vector2d(3, 3), new ArrayList<>(List.of(1,2,3))));
    }
    @Override
    public void run() {
        while(true) {
            for(Animal animal : animals) {
                animal.move();
            }
            mapChanged("moved animals");
            try {
                Thread.sleep(2000);
            } catch(InterruptedException err) {}
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
            listener.refreshSimulation(this, message);
        }
    }

    public List<Animal> getAnimals() {
        return animals;
    }
}
