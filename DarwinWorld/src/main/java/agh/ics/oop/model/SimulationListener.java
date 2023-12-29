package agh.ics.oop.model;
import agh.ics.oop.Simulation;
import agh.ics.oop.model.maps.WorldMap;

public interface SimulationListener {
    void refreshSimulation(Simulation simulation, String message);
}
