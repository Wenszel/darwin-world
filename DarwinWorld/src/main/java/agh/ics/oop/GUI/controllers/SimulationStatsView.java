package agh.ics.oop.GUI.controllers;

import agh.ics.oop.model.stats.SimulationStatistics;
import agh.ics.oop.model.stats.StatsFileManager;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.LinkedList;
import java.util.List;

public class SimulationStatsView {
    private final VBox simulationStatsBox;

    public SimulationStatsView(VBox simulationStatsBox) {
        this.simulationStatsBox = simulationStatsBox;
    }

    public void showSimulationStats(SimulationStatistics stats) {
        simulationStatsBox.getChildren().clear();
        List<Text> statsFields = new LinkedList<>(List.of(
                new Text("Day: " + stats.getCurrentDay()),
                new Text("Animals amount: " + stats.getAnimalsAmount()),
                new Text("Plants amount: " + stats.getPlantsAmount()),
                new Text("Empty fields: " + stats.getEmptyFieldsAmount()),
                new Text("Most popular genotypes: " + stats.getMostPopularGenotypes()),
                new Text("Average energy: " + stats.getAverageEnergy()),
                new Text("Average alive animals children: " + stats.getAverageAnimalsChildrenAmount()),
                new Text("Average dead animals life length: " + stats.getAverageDeadAnimalsLifeLength())));
        simulationStatsBox.getChildren().addAll(statsFields);
    }
}
