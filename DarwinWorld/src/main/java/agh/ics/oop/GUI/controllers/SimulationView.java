package agh.ics.oop.GUI.controllers;

import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationConfig;
import agh.ics.oop.model.SimulationListener;
import agh.ics.oop.model.mapElements.MapElement;
import agh.ics.oop.model.mapElements.MapField;
import agh.ics.oop.model.mapElements.VisualRepresentation;
import agh.ics.oop.model.maps.WorldMap;
import agh.ics.oop.model.utils.Vector2d;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Map;

public class SimulationView implements SimulationListener {
    @FXML
    private GridPane root;

    public void init(SimulationConfig config) {
        Simulation simulation = new Simulation(config);
        simulation.addSubscriber(this);
        Thread simulationTask = new Thread(simulation);
        simulationTask.start();
    }

    public void drawMap(Simulation simulation) {
        WorldMap map = simulation.getMap();
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
        for(Vector2d position : mapFields.keySet()) {
            for(MapElement element : map.objectsAt(position)) {
                VisualRepresentation visualRepresentation = element.getVisualRepresentation();
                if  (visualRepresentation.getBorder() != null) {
                    root.add(getBorderRectangle(element), element.getPosition().getX(), element.getPosition().getY());
                } else {
                    Rectangle elementUX = new Rectangle(50, 50);
                    elementUX.setFill(visualRepresentation.getBackground());
                    root.add(elementUX, element.getPosition().getX(), element.getPosition().getY());
                }
            }
        }
    }

    private StackPane getBorderRectangle(MapElement element) {
        Rectangle borderRect = new Rectangle(50, 50);
        borderRect.setFill(element.getVisualRepresentation().getBorder() != null ?
                element.getVisualRepresentation().getBorder() :
                Color.TRANSPARENT);
        Rectangle innerRect = new Rectangle(40, 40);
        innerRect.setFill(Color.WHITE);
        return new StackPane(borderRect, innerRect);
    }

    @Override
    public void refreshSimulation(Simulation simulation, String message) {
        System.out.println(message);
        Platform.runLater(() -> {
            drawMap(simulation);
        });
    }
}
