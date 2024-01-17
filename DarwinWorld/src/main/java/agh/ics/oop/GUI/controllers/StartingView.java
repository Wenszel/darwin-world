package agh.ics.oop.GUI.controllers;

import agh.ics.oop.model.config.SimulationConfig;
import agh.ics.oop.model.config.Parameter;
import agh.ics.oop.model.config.variants.MutationVariantName;
import agh.ics.oop.model.maps.MapType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
import java.util.*;

public class StartingView {
    @FXML private AnchorPane rootPane;
    @FXML private TextField mapWidth;
    @FXML private TextField mapHeight;
    @FXML private ComboBox<String> mapType;
    @FXML private ComboBox<String> mutationVariant;
    @FXML private TextField genotypeLengthInput;
    @FXML private TextField maxMutationsInput;
    @FXML private TextField minMutationsInput;
    @FXML private TextField startingPlantsInput;
    @FXML private TextField startingAnimalsInput;
    @FXML private TextField minReproductionEnergyInput;
    @FXML private TextField reproductionEnergyCostInput;
    @FXML private TextField startingEnergyInput;
    @FXML private TextField dailyEnergyCostInput;
    @FXML private TextField dailyPlantsGrowthInput;
    @FXML private TextField energyFromPlantInput;
    @FXML private Button loadFromCSVButton;
    @FXML private Button saveToCSVButton;
    @FXML private CheckBox saveStatsToCSVCheckBox;
    private final Map<Parameter, InputElement> inputElements = new HashMap<>();

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
        generateInputElements();
    }

    public void onSimulationStartClicked() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("views/simulationView.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add("views/simulationView.css");
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        SimulationView simulationController = loader.getController();
        SimulationConfig config = createConfigFromInputs();
        simulationController.init(config, stage);
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
                    try {
                        Parameter param = Parameter.valueOf(values[0]);
                        InputElement inputElement = inputElements.get(param);
                        if (inputElement != null) {
                            inputElement.set(values[1]);
                        } else {
                            System.out.println("Unknown param: " + values[0]);
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid parameter name: " + values[0]);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void save() {
        List<String> lines = getStringsWithParametersAndValues();

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

    private List<String> getStringsWithParametersAndValues() {
        List<String> strings = new LinkedList<>();
        for (Parameter param : Parameter.values()) {
            InputElement inputElement = inputElements.get(param);
            strings.add(param.name()+";"+inputElement.get());
        }
        return strings;
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
        Map<Parameter, String> params = new HashMap<>();
        for (Parameter param : Parameter.values()) {
            InputElement inputElement = inputElements.get(param);
            params.put(param, inputElement.get());
        }
        return new SimulationConfig(params);
    }

    private void generateInputElements() {
        inputElements.put(Parameter.MAP_WIDTH, new InputElement(() -> mapWidth.getText(), value -> mapWidth.setText(value)));
        inputElements.put(Parameter.MAP_HEIGHT, new InputElement(() -> mapHeight.getText(), value -> mapHeight.setText(value)));
        inputElements.put(Parameter.MAP_TYPE, new InputElement(() -> mapType.getValue(), value -> mapType.setValue(value)));
        inputElements.put(Parameter.MUTATION_TYPE, new InputElement(() -> mutationVariant.getValue(), value -> mutationVariant.setValue(value)));
        inputElements.put(Parameter.GENOTYPE_LENGTH, new InputElement(() -> genotypeLengthInput.getText(), value -> genotypeLengthInput.setText(value)));
        inputElements.put(Parameter.MAX_MUTATIONS, new InputElement(() -> maxMutationsInput.getText(), value -> maxMutationsInput.setText(value)));
        inputElements.put(Parameter.MIN_MUTATIONS, new InputElement(() -> minMutationsInput.getText(), value -> minMutationsInput.setText(value)));
        inputElements.put(Parameter.STARTING_PLANTS, new InputElement(() -> startingPlantsInput.getText(), value -> startingPlantsInput.setText(value)));
        inputElements.put(Parameter.STARTING_ANIMALS, new InputElement(() -> startingAnimalsInput.getText(), value -> startingAnimalsInput.setText(value)));
        inputElements.put(Parameter.MIN_REPRODUCTION_ENERGY, new InputElement(() -> minReproductionEnergyInput.getText(), value -> minReproductionEnergyInput.setText(value)));
        inputElements.put(Parameter.REPRODUCTION_ENERGY_COST, new InputElement(() -> reproductionEnergyCostInput.getText(), value -> reproductionEnergyCostInput.setText(value)));
        inputElements.put(Parameter.STARTING_ENERGY, new InputElement(() -> startingEnergyInput.getText(), value -> startingEnergyInput.setText(value)));
        inputElements.put(Parameter.DAILY_ENERGY_COST, new InputElement(() -> dailyEnergyCostInput.getText(), value -> dailyEnergyCostInput.setText(value)));
        inputElements.put(Parameter.DAILY_PLANTS_GROWTH, new InputElement(() -> dailyPlantsGrowthInput.getText(), value -> dailyPlantsGrowthInput.setText(value)));
        inputElements.put(Parameter.ENERGY_FROM_PLANT, new InputElement(() -> energyFromPlantInput.getText(), value -> energyFromPlantInput.setText(value)));
        inputElements.put(Parameter.SAVE_TO_CSV, new InputElement(() -> String.valueOf(saveStatsToCSVCheckBox.isSelected()), value -> saveStatsToCSVCheckBox.setSelected(Boolean.parseBoolean(value))));
    }

}