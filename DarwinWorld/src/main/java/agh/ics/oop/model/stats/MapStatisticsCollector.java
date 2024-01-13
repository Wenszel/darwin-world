package agh.ics.oop.model.stats;

import agh.ics.oop.model.elements.Animal;
import agh.ics.oop.model.elements.MapField;

public class MapStatisticsCollector {
    private int animalsAmount = 0;
    private int plantsAmount = 0;
    private double totalEnergy = 0;
    private  double totalChildren = 0;
    private int emptyFieldsAmount = 0;

    public double getAverageEnergy() {
        return animalsAmount ==0 ? 0 : totalEnergy/animalsAmount;
    }
    public int getAnimalsAmount() {
        return animalsAmount;
    }
    public int getPlantsAmount() {
        return plantsAmount;
    }

    public int getEmptyFieldsAmount() {
        return emptyFieldsAmount;
    }

    public double getAverageAnimalsChildrenAmount() {
        return animalsAmount == 0 ? 0 : totalChildren/animalsAmount;
    }

    public void collectAnimalsData(MapField mapField) {
        animalsAmount += mapField.getAnimalsOnField().size();
        for (Animal animal : mapField.getAnimalsOnField()) {
            totalEnergy += animal.getEnergy();
            totalChildren += animal.getChildrenAmount();
        }
    }

    public void increaseEmptyFieldAmount() {
        emptyFieldsAmount += 1;
    }

    public void increasePlantsAmount() {
        plantsAmount += 1;
    }
}
