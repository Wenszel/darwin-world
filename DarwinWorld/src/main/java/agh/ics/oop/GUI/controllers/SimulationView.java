package agh.ics.oop.GUI.controllers;

import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationConfig;
import agh.ics.oop.model.SimulationListener;
import agh.ics.oop.model.mapElements.*;
import agh.ics.oop.model.maps.WorldMap;
import agh.ics.oop.model.stats.AnimalStatistics;
import agh.ics.oop.model.utils.Vector2d;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SimulationView implements SimulationListener {
    @FXML
    private Pane mapBox;
    @FXML
    private VBox chartLegend;
    @FXML
    private VBox statsBox;
    private Animal observedAnimal;

    public void init(SimulationConfig config) {
        Simulation simulation = new Simulation(config);
        simulation.addSubscriber(this);
        Thread simulationTask = new Thread(simulation);
        simulationTask.start();
    }

    public void drawMap(Simulation simulation) {
        mapBox.getChildren().clear();
        Stage stage = (Stage) mapBox.getScene().getWindow();
        mapBox.prefWidthProperty().bind(stage.widthProperty().multiply(0.5));
        mapBox.prefHeightProperty().bind(stage.heightProperty().subtract(100));
        chartLegend.prefWidthProperty().bind(stage.widthProperty().multiply(0.28));
        statsBox.prefWidthProperty().bind(stage.widthProperty().multiply(0.22));

        WorldMap map = simulation.getMap();
        Map<Vector2d, MapField> mapFields = map.getMapFields();

        double fieldWidth = mapBox.getPrefWidth() / map.getWidth();
        double fieldHeight = mapBox.getPrefHeight() / map.getHeight();

        Canvas canvas = new Canvas(mapBox.getWidth(), mapBox.getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        canvas.setOnMouseClicked(event -> {
            System.out.println("Clicked on: " + event.getX() + " " + event.getY());
            Vector2d clickedPosition = new Vector2d((int) (event.getX() / fieldWidth), (int) (event.getY() / fieldHeight));
            if(mapFields.get(clickedPosition) !=null && !mapFields.get(clickedPosition).getAnimalsOnField().isEmpty()) {
                this.observedAnimal = mapFields.get(clickedPosition).getAnimalsOnField().get(0);
            }
            System.out.println("Clicked on: " + clickedPosition);
        });
        mapBox.getChildren().add(canvas);
        for (Vector2d position : mapFields.keySet()) {
            if (mapFields.get(position).isPreferred()) {
                gc.setFill(Color.GRAY);
                gc.fillRect(position.getX() * fieldWidth, position.getY() * fieldHeight, fieldWidth, fieldHeight);
            }
            if(mapFields.get(position).getAnimalsOnField().size() > 0) {
                Animal animal = mapFields.get(position).getAnimalsOnField().get(0);
                if(animal==observedAnimal) {
                }
                animal.drawOnMap(gc, fieldWidth, fieldHeight);
            } else if(mapFields.get(position).getHasPlant()) {
                Plant plant = mapFields.get(position).getPlant();
                plant.drawOnMap(gc, fieldWidth, fieldHeight);
            }
            for(MapElement element : map.stackObjectsToDraw(position)) {
                element.drawOnMap(gc, fieldWidth, fieldHeight);
            }
        }
        if(observedAnimal != null) {
            showAnimalStats(observedAnimal);
        }
    }
    private void showAnimalStats(Animal animal) {
        statsBox.getChildren().clear();
        AnimalStatistics stats = animal.getAnimalStats();
        List<Text> statsFields = new LinkedList<>(List.of(new Text("Genotype: " + stats.getGenotype()),
                new Text("Active gene: " + animal.getGenotype().getCurrentGene()), new Text("Energy: " + stats.getEnergy()),
                new Text("Eaten plants: " + stats.getEatenPlants()), new Text("Children amount: " + stats.getChildrenAmount()),
        new Text("Descendant amount: " + stats.getDescendantAmount()), new Text("Day alive: " + stats.getDayAlive()),
        new Text("Death day: " + stats.getDeathDay())));
        statsBox.getChildren().addAll(statsFields);

    }
    @Override
    public void refreshSimulation(Simulation simulation, String message) {
        System.out.println(message);
        Task<Void> task = new Task<>() {
            @Override
            public Void call() {
                drawMap(simulation);
                return null;
            }
        };
        Platform.runLater(task);
    }
}
