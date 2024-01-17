package agh.ics.oop.model.stats;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StatsFileManager {
    UUID mapID;

    public StatsFileManager(UUID mapID) {
        this.mapID = mapID;
        try(FileWriter fileWriter = new FileWriter(mapID.toString() + ".csv", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            List<String> titles = new ArrayList<>(List.of(
                    "Day", "AnimalsAmount", "PlantsAmount", "EmptyFields", "MostPopularGenotype", "AverageEnergy", "AverageAliveAnimalsChildren",
                    "AverageDeadAnimalsLifeLength"));
            titles.replaceAll(s -> s + ";");
            for (String title: titles) {
                bufferedWriter.write(title);
            }
            bufferedWriter.newLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save(SimulationStatistics statistics) {
        try(FileWriter fileWriter = new FileWriter(mapID.toString() + ".csv", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            String space = "; ";
            bufferedWriter.write(statistics.getCurrentDay() + space);
            bufferedWriter.write(statistics.getAnimalsAmount() + space);
            bufferedWriter.write(statistics.getPlantsAmount() + space);
            bufferedWriter.write(statistics.getEmptyFieldsAmount() + space);
            bufferedWriter.write(statistics.getMostPopularGenotypes().get(0).getKey() + space);
            bufferedWriter.write(statistics.getAverageEnergy() + space);
            bufferedWriter.write(statistics.getAverageAnimalsChildrenAmount() + space);
            bufferedWriter.write(Double.toString(statistics.getAverageDeadAnimalsLifeLength()));
            bufferedWriter.newLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
