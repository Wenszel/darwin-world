package agh.ics.oop.GUI.controllers;

import agh.ics.oop.DayManager;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

public class ChartView {
    private final XYChart.Series<Integer, Integer> series;
    private final DayManager dayManager;

    public ChartView(LineChart<Integer, Integer> populationChart, DayManager dayManager) {
        series = new XYChart.Series<>();
        populationChart.setLegendVisible(false);
        populationChart.getData().add(series);
        this.dayManager = dayManager;
    }

    public void addDayDataToChart() {
        series.getData().add(new XYChart.Data<>(dayManager.getDay(), dayManager.getPopulation()));
    }
}
