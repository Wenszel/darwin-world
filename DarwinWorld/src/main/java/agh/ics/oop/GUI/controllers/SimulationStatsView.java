package agh.ics.oop.GUI.controllers;

import agh.ics.oop.model.stats.SimulationStatistics;
import agh.ics.oop.model.utils.Genotype;
import agh.ics.oop.model.utils.KeyValue;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SimulationStatsView {
    private final VBox simulationStatsBox;
    private final TableView<Map.Entry<String, String>> tableView;
    private static final String STATISTIC_COLUMN_NAME = "Statistic";
    private static final String VALUE_COLUMN_NAME = "Value";

    public SimulationStatsView(VBox simulationStatsBox) {
        this.simulationStatsBox = simulationStatsBox;

        TableColumn<Map.Entry<String, String>, String> statNameColumn = new TableColumn<>(STATISTIC_COLUMN_NAME);
        TableColumn<Map.Entry<String, String>, String> statValueColumn = new TableColumn<>(VALUE_COLUMN_NAME);
        statNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey()));
        statValueColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue()));

        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        statNameColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.5));
        statValueColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.5));
        tableView.getColumns().addAll(statNameColumn, statValueColumn);
    }

    public void showSimulationStats(SimulationStatistics stats) {
        simulationStatsBox.getChildren().clear();

        Map<String, String> statsMap = new LinkedHashMap<>();
        statsMap.put("Day", String.valueOf(stats.getCurrentDay()));
        statsMap.put("Animals amount", String.valueOf(stats.getAnimalsAmount()));
        statsMap.put("Plants amount", String.valueOf(stats.getPlantsAmount()));
        statsMap.put("Empty fields", String.valueOf(stats.getEmptyFieldsAmount()));
        statsMap.put("Most popular genotypes", formatGenotypes(stats.getMostPopularGenotypes()));
        statsMap.put("Average energy", formatDouble(stats.getAverageEnergy()));
        statsMap.put("Average alive animals children", formatDouble(stats.getAverageAnimalsChildrenAmount()));
        statsMap.put("Average dead animals life length", formatDouble(stats.getAverageDeadAnimalsLifeLength()));
        ObservableList<Map.Entry<String, String>> items = FXCollections.observableArrayList(statsMap.entrySet());
        tableView.setItems(items);

        simulationStatsBox.getChildren().add(tableView);
    }

    private String formatGenotypes(List<KeyValue<Genotype, Integer>> genotypes) {
        StringBuilder result = new StringBuilder();
        for (KeyValue<Genotype, Integer> genotype : genotypes) {
            result.append(genotype.getKey()).append(" (").append(genotype.getValue()).append(") \n");
        }
        return result.toString();
    }

    private String formatDouble(double number) {
        return String.format("%.2f", number);
    }
}
