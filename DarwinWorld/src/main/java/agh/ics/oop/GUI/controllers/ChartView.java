package agh.ics.oop.GUI.controllers;

import agh.ics.oop.DayManager;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

public class ChartView {
    private final XYChart.Series<Integer, Integer> animalSeries;
    private final XYChart.Series<Integer, Integer> grassSeries;
    private final DayManager dayManager;

    public ChartView(LineChart<Integer, Integer> populationChart, DayManager dayManager) {

        populationChart.setLegendVisible(false);
        animalSeries = new XYChart.Series<>();
        grassSeries = new XYChart.Series<>();
        populationChart.getData().add(animalSeries);
        populationChart.getData().add(grassSeries);
        this.dayManager = dayManager;
    }

    public void addDayDataToChart(int animalAmount, int grassAmount) {
        animalSeries.getData().add(new XYChart.Data<>(dayManager.getDay(), animalAmount));
        grassSeries.getData().add(new XYChart.Data<>(dayManager.getDay(), grassAmount));
    }
}
