package agh.ics.oop.GUI.controllers;

import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationConfig;
import agh.ics.oop.model.SimulationListener;
import agh.ics.oop.model.mapElements.*;
import agh.ics.oop.model.maps.WorldMap;
import agh.ics.oop.model.stats.MapStatisticsCollector;
import agh.ics.oop.model.stats.SimulationStatistics;
import agh.ics.oop.model.stats.SimulationStatisticsBuilder;
import agh.ics.oop.model.stats.StatsFileManager;
import agh.ics.oop.model.utils.Genotype;
import agh.ics.oop.model.utils.Vector2d;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.LineChart;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Map;

public class SimulationView implements SimulationListener {
    @FXML
    private Pane mapBox;
    @FXML
    private SplitPane leftSide;
    @FXML
    private SplitPane rightSide;
    @FXML
    private VBox simulationStatsBox;
    private SimulationStatsView simulationStatsView;
    @FXML
    private VBox animalStatsBox;
    private AnimalStatsView animalStatsView;
    @FXML
    private LineChart<Integer, Integer> populationChart;
    private ChartView chartView;
    private Animal observedAnimal;
    private Simulation simulation;
    private boolean isPreferableShown = false;
    private boolean isDominantGenotypeShown = false;
    private boolean isSavingToCSVTurnedOn = false;
    private StatsFileManager statsFileManager;

    public void init(SimulationConfig config, Stage stage) {
        simulation = new Simulation(config);
        simulation.addSubscriber(this);
        simulationStatsView = new SimulationStatsView(simulationStatsBox);
        animalStatsView = new AnimalStatsView(animalStatsBox);
        chartView = new ChartView(populationChart, simulation.getDayManager());
        isSavingToCSVTurnedOn = config.getSaveToCSV();
        if (isSavingToCSVTurnedOn) {
            statsFileManager = new StatsFileManager(simulation.getMap().getId());
        }
        Thread simulationTask = new Thread(simulation);
        simulationTask.start();
        stage.setOnCloseRequest(event -> {
            simulationTask.interrupt();
        });
    }

    private void handleCanvasClick(MouseEvent event, double fieldWidth, double fieldHeight, Map<Vector2d, MapField> mapFields) {
        Vector2d clickedPosition = new Vector2d((int) (event.getX() / fieldWidth), (int) (event.getY() / fieldHeight));
        if (mapFields.get(clickedPosition) != null && mapFields.get(clickedPosition).getAnimalsOnField() != null) {
            if (observedAnimal != null) {
                // It is faster to unmark all animals from list of animals
                // than unmark the same way as mark (i mean recursively)
                // we do not need to unmark dead animals because they can no longer reproduce
               simulation.getMap().unmarkAllAnimalsAsDescendants();
            }
            observedAnimal = mapFields.get(clickedPosition).getAnimalsOnField().get(0);
            // Added additional thread to avoid freezing the GUI to calculate descendants if there are to many of them
            Thread thread = new Thread(() -> {
                observedAnimal.markAsDescendant(observedAnimal);
                Platform.runLater(() -> animalStatsView.showAnimalStats(observedAnimal));
            });
            thread.start();
        }
    }

    public void drawSimulationWindow() {
        clearView();

        MapStatisticsCollector mapStatisticsCollector = new MapStatisticsCollector();
        WorldMap map = simulation.getMap();
        Map<Vector2d, MapField> mapFields = map.getMapFields();

        double fieldWidth = mapBox.getPrefWidth() / map.getWidth();
        double fieldHeight = mapBox.getPrefHeight() / map.getHeight();

        GraphicsContext gc = createCanvas(fieldWidth, fieldHeight, mapFields);

        for (Vector2d position : mapFields.keySet()) {
            MapField mapField = mapFields.get(position);
            if (!mapField.getAnimalsOnField().isEmpty()) {
                mapStatisticsCollector.collectAnimalsData(mapField);
                drawAnimalOnMap(mapField, gc, fieldWidth, fieldHeight);
            } else if (mapFields.get(position).getHasPlant()) {
                mapStatisticsCollector.increasePlantsAmount();
                mapField.getPlant().drawOnMap(gc, fieldWidth, fieldHeight);
            } else {
                mapStatisticsCollector.increaseEmptyFieldAmount();
            }
            for (MapElement element : map.stackObjectsToDraw(position)) {
                element.drawOnMap(gc, fieldWidth, fieldHeight);
            }
            if (mapFields.get(position).isPreferred() && isPreferableShown) {
                gc.setFill(Color.GRAY);
                gc.fillRect(position.getX() * fieldWidth, position.getY() * fieldHeight, fieldWidth, fieldHeight);
            }
        }
        if (observedAnimal != null) {
            animalStatsView.showAnimalStats(observedAnimal);
        }
        SimulationStatistics stats = new SimulationStatisticsBuilder()
                .collectMapStatistics(mapStatisticsCollector)
                .setCurrentDay(simulation.getDayManager().getDay())
                .setAverageDeadAnimalsLifeLength(simulation.getDayManager().getDeadAnimalsAverageLifeLength())
                .setMostPopularGenotypes(simulation.getMap().getMostPopularGenotypes())
                .build();
        simulationStatsView.showSimulationStats(stats);
        chartView.addDayDataToChart(stats.getAnimalsAmount(), stats.getPlantsAmount());
        if (isSavingToCSVTurnedOn) {
            statsFileManager.save(stats);
        }
    }

    private void drawAnimalOnMap(MapField mapField, GraphicsContext gc, double fieldWidth, double fieldHeight) {
        if (isDominantGenotypeShown) {
            Genotype mostPopularGenotype = simulation.getMap().getMostPopularGenotypes().get(0).getKey();
            for (Animal animal: mapField.getAnimalsOnField()) {
                if (animal.getGenotype().equals(mostPopularGenotype)) {
                    animal.drawOnMap(gc, fieldWidth, fieldHeight);
                    return;
                }
            }
        } else {
            mapField.getAnimalsOnField().get(0).drawOnMap(gc, fieldWidth, fieldHeight);
        }
    }

    private void clearView() {
        mapBox.getChildren().clear();
        Stage stage = (Stage) mapBox.getScene().getWindow();
        mapBox.prefWidthProperty().bind(stage.widthProperty().multiply(0.5));
        mapBox.prefHeightProperty().bind(stage.heightProperty());
        leftSide.prefWidthProperty().bind(stage.widthProperty().multiply(0.28));
        rightSide.prefWidthProperty().bind(stage.widthProperty().multiply(0.22));
    }

    private GraphicsContext createCanvas(double fieldWidth, double fieldHeight, Map<Vector2d, MapField> mapFields) {
        Canvas canvas = new Canvas(mapBox.getWidth(), mapBox.getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        canvas.setOnMouseClicked(event -> handleCanvasClick(event, fieldWidth, fieldHeight, mapFields));
        mapBox.getChildren().add(canvas);
        return gc;
    }

    @Override
    public void refreshSimulation(String message) {
        System.out.println(message);
        Task<Void> task = new Task<>() {
            @Override
            public Void call() {
                drawSimulationWindow();
                return null;
            }
        };
        Platform.runLater(task);
    }

    public void pause() {
        simulation.pause();
    }

    public void resume() {
        simulation.resume();
        isPreferableShown = false;
        isDominantGenotypeShown = false;
    }

    public void handlePreferableFieldsDisplay() {
        if (simulation.isPaused()) {
            isPreferableShown = !isPreferableShown;
            drawSimulationWindow();
        }
    }

    public void handleDominantGenotypeDisplay() {
        if (simulation.isPaused()) {
            isDominantGenotypeShown = !isDominantGenotypeShown;
            drawSimulationWindow();
        }
    }
    public void speedUpSimulation() {
        if(!simulation.isPaused()) {
            simulation.increaseSimulationSpeed();
        }
    }
    public void slowDownSimulation() {
        if(!simulation.isPaused()) {
            simulation.decreaseSimulationSpeed();
        }
    }
}
