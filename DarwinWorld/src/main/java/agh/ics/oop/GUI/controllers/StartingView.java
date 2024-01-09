package agh.ics.oop.GUI.controllers;

import agh.ics.oop.SimulationConfig;
import agh.ics.oop.model.Config.Parameter;
import agh.ics.oop.model.Config.variants.MutationVariantName;
import agh.ics.oop.model.maps.MapType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class StartingView {
    @FXML private AnchorPane rootPane;
    @FXML private TextField mapWidth;
    @FXML private TextField mapHeight;
    @FXML private ComboBox<String> mapType;
    @FXML private ComboBox<String> mutationVariant;
    @FXML private TextField genotypeLengthInput;
    @FXML private TextField maxMutationsInput;
    @FXML private TextField minMutationsInput;
    @FXML private TextField minReproductionEnergyInput;
    @FXML private TextField reproductionEnergyCostInput;
    @FXML private TextField startingEnergyInput;
    @FXML private TextField dailyEnergyCostInput;
    @FXML private TextField dailyPlantsGrowthInput;
    @FXML private TextField energyFromPlantInput;
    @FXML private Button loadFromCSVButton;
    @FXML private Button saveToCSVButton;
    public void init() {
        Image backgroundImage = new Image("/images/background.jpg");
        BackgroundImage background = new BackgroundImage(backgroundImage,
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        rootPane.setBackground(new Background(background));
        for (MapType type : MapType.values()) {
            mapType.getItems().add(type.toString());
        }
        for (MutationVariantName type : MutationVariantName.values()) {
            mutationVariant.getItems().add(type.toString());
        }
    }

    public void onSimulationStartClicked() throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("views/simulationView.fxml"));
        Scene scene = new Scene(loader.load(), 1000, 800);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.show();

        SimulationView simulationController = loader.getController();
        SimulationConfig config = createConfigFromInputs();
        simulationController.init(config);
    }
    @FXML
    private void load() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load from CSV");
        FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("CSV files", "*.csv");
        fileChooser.getExtensionFilters().add(csvFilter);

        File selectedFile = fileChooser.showOpenDialog(loadFromCSVButton.getScene().getWindow());

        if (selectedFile != null) {
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(selectedFile), StandardCharsets.UTF_8))){
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] values = line.split(";");
                    switch (values[0]) {
                        case "MAP_WIDTH":
                            mapWidth.setText(values[1]);
                            break;
                        case "MAP_HEIGHT":
                            mapHeight.setText(values[1]);
                            break;
                        case "MAP_TYPE":
                            try {
                                MapType map = MapType.valueOf(values[1]);
                                mapType.setValue(map.toString());
                            }catch (IllegalArgumentException err){
                                System.out.println(err);
                            };
                            break;
                        case "MUTATION_VARIANT":
                            try {
                                MutationVariantName variant = MutationVariantName.valueOf(values[1]);
                                mutationVariant.setValue(variant.toString());
                            }catch (IllegalArgumentException err){
                                System.out.println(err);
                            };
                            break;
                        case "GENOTYPE_LENGTH":
                            genotypeLengthInput.setText(values[1]);
                            break;
                        case "MAX_MUTATIONS":
                            maxMutationsInput.setText(values[1]);
                            break;
                        case "MIN_MUTATIONS":
                            minMutationsInput.setText(values[1]);
                            break;
                        case "MIN_REPRODUCTION_ENERGY":
                            minReproductionEnergyInput.setText(values[1]);
                            break;
                        case "REPRODUCTION_ENERGY_COST":
                            reproductionEnergyCostInput.setText(values[1]);
                            break;
                        case "STARTING_ENERGY":
                            startingEnergyInput.setText(values[1]);
                            break;
                        case "DAILY_ENERGY_COST":
                            dailyEnergyCostInput.setText(values[1]);
                            break;
                        case "DAILY_PLANTS_GROWTH":
                            dailyPlantsGrowthInput.setText(values[1]);
                            break;
                        case "ENERGY_FROM_PLANT":
                            energyFromPlantInput.setText(values[1]);
                            break;
                        default:
                            System.out.println("Unknown param: " + values[0]);
                            break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void save() {
        List<String> lines = getStrings();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save to CSV");
        FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("CSV Files", "*.csv");
        fileChooser.getExtensionFilters().add(csvFilter);

        File selectedFile = fileChooser.showSaveDialog(saveToCSVButton.getScene().getWindow());

        if (selectedFile != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
                System.out.println("Saved " + selectedFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<String> getStrings() {
        String mapWidthValue = "MAP_WIDTH;" + mapWidth.getText();
        String mapHeightValue = "MAP_HEIGHT;" + mapHeight.getText();
        String mapTypeValue = "MAP_TYPE;" + mapType.getValue();
        String genotypeLengthValue = "GENOTYPE_LENGTH;" + genotypeLengthInput.getText();
        String mutationVariantValue = "MUTATION_VARIANT;" + mutationVariant.getValue();
        String maxMutationsValue = "MAX_MUTATIONS;" + maxMutationsInput.getText();
        String minMutationsValue = "MIN_MUTATIONS;" + minMutationsInput.getText();
        String minReproductionEnergyValue = "MIN_REPRODUCTION_ENERGY;" + minReproductionEnergyInput.getText();
        String reproductionEnergyCostValue = "REPRODUCTION_ENERGY_COST;" + reproductionEnergyCostInput.getText();
        String startingEnergyValue = "STARTING_ENERGY;" + startingEnergyInput.getText();
        String dailyEnergyCostValue = "DAILY_ENERGY_COST;" + dailyEnergyCostInput.getText();
        String dailyPlantsGrowthValue = "DAILY_PLANTS_GROWTH;" + dailyPlantsGrowthInput.getText();
        String energyFromPlantValue = "ENERGY_FROM_PLANT;" + energyFromPlantInput.getText();

        List<String> lines = Arrays.asList(mapWidthValue, mapHeightValue,mapTypeValue, mutationVariantValue, genotypeLengthValue,
                maxMutationsValue, minMutationsValue,minReproductionEnergyValue,
                reproductionEnergyCostValue, startingEnergyValue, dailyEnergyCostValue, dailyPlantsGrowthValue, energyFromPlantValue);
        return lines;
    }

    @FXML
    private void increment(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        TextField sourceTextField = (TextField) sourceButton.getParent().getChildrenUnmodifiable().get(2);
        int value = Integer.parseInt(sourceTextField.getText()) + 1;
        sourceTextField.setText(Integer.toString(value));
    }

    @FXML
    private void decrement(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        TextField sourceTextField = (TextField) sourceButton.getParent().getChildrenUnmodifiable().get(2);
        int value = Integer.parseInt(sourceTextField.getText()) - 1;
        if (value < 1) {
            value = 1;
        }
        sourceTextField.setText(Integer.toString(value));
    }


    private SimulationConfig createConfigFromInputs() {
        HashMap<Parameter, String> params = new HashMap<>();
        try {
            params.put(Parameter.MAP_WIDTH, mapWidth.getText());
            params.put(Parameter.MAP_HEIGHT, mapHeight.getText());
            params.put(Parameter.MAP_TYPE, mapType.getValue());
            params.put(Parameter.MUTATION_TYPE, mutationVariant.getValue());
            params.put(Parameter.GENOTYPE_LENGTH, genotypeLengthInput.getText());
            params.put(Parameter.MAX_MUTATIONS, maxMutationsInput.getText());
            params.put(Parameter.MIN_MUTATIONS, minMutationsInput.getText());
            params.put(Parameter.MIN_REPRODUCTION_ENERGY, minReproductionEnergyInput.getText());
            params.put(Parameter.REPRODUCTION_ENERGY_COST, reproductionEnergyCostInput.getText());
            params.put(Parameter.STARTING_ENERGY, startingEnergyInput.getText());
            params.put(Parameter.DAILY_ENERGY_COST, dailyEnergyCostInput.getText());
            params.put(Parameter.DAILY_PLANTS_GROWTH, dailyPlantsGrowthInput.getText());
            params.put(Parameter.ENERGY_FROM_PLANT, energyFromPlantInput.getText());
        } catch (NumberFormatException e) {
            System.out.println("Incorrect values");
        }
        return new SimulationConfig(params);
    }
}