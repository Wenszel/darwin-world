package agh.ics.oop.GUI.controllers;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.SimulationListener;
import agh.ics.oop.model.mapElements.Animal;
import agh.ics.oop.model.mapElements.MapField;
import agh.ics.oop.model.maps.GlobeMap;
import agh.ics.oop.model.maps.WorldMap;
import agh.ics.oop.model.utils.Vector2d;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;
import java.util.Map;


public class SimulationView implements SimulationListener {
    private final int SIZE = 10;
    @FXML
    private GridPane root;

    public void init() {
        GlobeMap map = new GlobeMap(10, 10);
        Simulation simulation = new Simulation(map);
        simulation.addSubscriber(this);
        Thread simulationTask = new Thread(simulation);
        simulationTask.start();
    }
    public void drawMap(Simulation simulation) {
        GlobeMap map = simulation.getMap();
        Map<Vector2d,MapField> mapFields = map.getMapFields();

        for (int y = 0; y < map.getWidth(); y++) {
            for (int x = 0; x < map.getHeight(); x++) {
                Rectangle rect = new Rectangle(50, 50);
                rect.setStroke(Color.BLACK);
                rect.setFill(Color.WHITE);
                root.add(rect, x, y);
            }
        }
        for(MapField field : mapFields.values()) {
            for(Animal animal : field.getAnimalsOnField()) {
                Rectangle animalUX = new Rectangle(50, 50);
                animalUX.setFill(Color.GREEN);
                root.add(animalUX, animal.getPosition().getX(), animal.getPosition().getY());
            }
        }


    }

    @Override
    public void refreshSimulation(Simulation simulation, String message) {
        System.out.println(message);
        Platform.runLater(() -> {
            drawMap(simulation);
        });

    }
}
