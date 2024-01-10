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
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Map;

public class SimulationView implements SimulationListener {
    @FXML
    private GridPane mapGrid;
    @FXML
    private VBox chartLegend;
    @FXML
    private VBox stats;

    public void init(SimulationConfig config) {
        Simulation simulation = new Simulation(config);
        simulation.addSubscriber(this);
        Thread simulationTask = new Thread(simulation);
        simulationTask.start();
    }

    public void drawMap(Simulation simulation) {
        mapGrid.getChildren().clear();
        Stage stage = (Stage) mapGrid.getScene().getWindow();
        mapGrid.prefWidthProperty().bind(stage.widthProperty().multiply(0.5));
        mapGrid.prefHeightProperty().bind(stage.heightProperty().subtract(100));
        chartLegend.prefWidthProperty().bind(stage.widthProperty().multiply(0.28));
        stats.prefWidthProperty().bind(stage.widthProperty().multiply(0.22));

        WorldMap map = simulation.getMap();
        Map<Vector2d,MapField> mapFields = map.getMapFields();

        double fieldWidth = mapGrid.getPrefWidth()/map.getWidth();
        double fieldHeight = mapGrid.getPrefHeight()/map.getHeight();
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                Rectangle rect = new Rectangle(fieldWidth, fieldHeight);
                if(mapFields.get(new Vector2d(x, y)).isPreferred()) {
                    rect.setFill(Color.GRAY);
                } else {
                    rect.setFill(Color.WHITE);
                }
                mapGrid.add(rect, x,y);
            }
        }
        for(Vector2d position : mapFields.keySet()) {
            for(MapElement element : map.objectsAt(position)) {
                VisualRepresentation visualRepresentation = element.getVisualRepresentation();
                if  (visualRepresentation.getBorder() != null) {
                    mapGrid.add(getBorderRectangle(element, fieldWidth, fieldHeight), element.getPosition().getX(), element.getPosition().getY());
                } else {
                    Rectangle elementUX = new Rectangle(fieldWidth, fieldHeight);
                    elementUX.setFill(visualRepresentation.getBackground());
                    mapGrid.add(elementUX, element.getPosition().getX(), element.getPosition().getY());
                }
            }
        }
    }

    private StackPane getBorderRectangle(MapElement element, double fieldWidth, double fieldHeight) {
        Rectangle borderRect = new Rectangle(fieldWidth, fieldHeight);
        borderRect.setFill(element.getVisualRepresentation().getBorder() != null ?
                element.getVisualRepresentation().getBorder() :
                Color.TRANSPARENT);
        Rectangle innerRect = new Rectangle(fieldWidth/2, fieldHeight/2);
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
