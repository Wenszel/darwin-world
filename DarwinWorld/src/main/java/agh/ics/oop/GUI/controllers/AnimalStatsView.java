package agh.ics.oop.GUI.controllers;

import agh.ics.oop.model.mapElements.Animal;
import agh.ics.oop.model.stats.AnimalStatistics;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.LinkedList;
import java.util.List;

public class AnimalStatsView {
    private final VBox animalStatsBox;

    public AnimalStatsView(VBox animalStatsBox) {
        this.animalStatsBox = animalStatsBox;
    }

    public void showAnimalStats(Animal animal) {
        animalStatsBox.getChildren().clear();
        AnimalStatistics stats = animal.getAnimalStats();
        List<Text> statsFields = new LinkedList<>(
                List.of(
                        new Text("Genotype: " + stats.getGenotype()),
                        new Text("Active gene: " + animal.getGenotype().getCurrentGene()),
                        new Text("Energy: " + stats.getEnergy()),
                        new Text("Eaten plants: " + stats.getEatenPlants()),
                        new Text("Children amount: " + stats.getChildrenAmount()),
                        new Text("Descendant amount: " + stats.getDescendantAmount()),
                        new Text("Day alive: " + stats.getDayAlive()),
                        new Text("Death day: " + stats.getDeathDay())
                )
        );
        animalStatsBox.getChildren().addAll(statsFields);
    }
}
