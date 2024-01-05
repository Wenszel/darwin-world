package agh.ics.oop.GUI.controllers;

import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationConfig;
import agh.ics.oop.model.Config.Parameter;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SimulationView implements SimulationListener {
    @FXML
    private GridPane root;

    public void init() {
        HashMap<Parameter, Integer> params = new HashMap<>();
        params.put(Parameter.MAP_WIDTH, 6);
        params.put(Parameter.MAP_HEIGHT, 6);
        params.put(Parameter.GENOTYPE_LENGTH, 5);
        params.put(Parameter.MIN_REPRODUCTION_ENERGY, 30);
        params.put(Parameter.REPRODUCTION_ENERGY_COST, 30);
        params.put(Parameter.STARTING_ENERGY, 50);
        params.put(Parameter.DAILY_ENERGY_COST, 1);

        Simulation simulation = new Simulation(new SimulationConfig(params));
        simulation.addSubscriber(this);
        Thread simulationTask = new Thread(simulation);
        simulationTask.start();
    }
    public void drawMap(Simulation simulation) {
        GlobeMap map = simulation.getMap();
        Map<Vector2d,MapField> mapFields = map.getMapFields();


        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                Rectangle rect = new Rectangle(50, 50);
                rect.setStroke(Color.BLACK);
                if(mapFields.get(new Vector2d(x, y)).isPreferred()) {
                    rect.setFill(Color.GRAY);
                } else {
                    rect.setFill(Color.WHITE);
                }

                root.add(rect, x,y);
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
