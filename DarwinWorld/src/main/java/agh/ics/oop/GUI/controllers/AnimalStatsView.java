package agh.ics.oop.GUI.controllers;

import agh.ics.oop.model.mapElements.Animal;
import agh.ics.oop.model.stats.AnimalStatistics;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import java.util.LinkedHashMap;
import java.util.Map;

public class AnimalStatsView {
    private final VBox animalStatsBox;
    private final TableView<Map.Entry<String, String>> tableView;
    private static final String STATISTIC_COLUMN_NAME = "Statistic";
    private static final String VALUE_COLUMN_NAME = "Value";

    public AnimalStatsView(VBox animalStatsBox) {
        this.animalStatsBox = animalStatsBox;

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

    public void showAnimalStats(Animal animal) {
        animalStatsBox.getChildren().clear();

        AnimalStatistics stats = animal.getAnimalStats();
        Map<String, String> statsMap = new LinkedHashMap<>();

        statsMap.put("Genotype", stats.getGenotype().toString());
        statsMap.put("Active gene", String.valueOf(animal.getGenotype().getCurrentGene()));
        statsMap.put("Energy", String.valueOf(stats.getEnergy()));
        statsMap.put("Eaten plants", String.valueOf(stats.getEatenPlants()));
        statsMap.put("Children amount", String.valueOf(stats.getChildrenAmount()));
        statsMap.put("Descendant amount", String.valueOf(stats.getDescendantAmount()));
        statsMap.put("Day alive", String.valueOf(stats.getDayAlive()));
        statsMap.put("Death day", stats.getDeathDay() == 0 ? "Still alive" : String.valueOf(stats.getDeathDay()));

        ObservableList<Map.Entry<String, String>> items = FXCollections.observableArrayList(statsMap.entrySet());
        tableView.setItems(items);

        animalStatsBox.getChildren().add(tableView);
    }
}