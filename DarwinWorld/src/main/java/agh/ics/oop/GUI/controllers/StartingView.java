package agh.ics.oop.GUI.controllers;

import agh.ics.oop.SimulationConfig;
import agh.ics.oop.model.Config.Parameter;
import agh.ics.oop.model.Config.variants.MutationVariantName;
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
    private final Map<Parameter, SetAction> setAction = new HashMap<>();
    private final Map<Parameter, GetAction> getActions = new HashMap<>();

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
        generateInputSetters();
        generateInputGetters();
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
                    try {
                        Parameter param = Parameter.valueOf(values[0]);
                        SetAction action = setAction.get(param);
                        if (action != null) {
                            action.set(values[1]);
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
        List<String> strings = new LinkedList<>();
        for (Parameter param : Parameter.values()) {
            GetAction getter = getActions.get(param);
            if (getter != null) {
                strings.add(param.name()+";"+getter.get());
            }
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
            GetAction getter = getActions.get(param);
            if (getter != null) {
                params.put(param, getter.get());
            }
        }
        return new SimulationConfig(params);
    }

    private void generateInputSetters() {
        setAction.put(Parameter.MAP_WIDTH, mapWidth::setText);
        setAction.put(Parameter.MAP_HEIGHT, mapHeight::setText);
        setAction.put(Parameter.MAP_TYPE, value -> {
            try {
                MapType map = MapType.valueOf(value);
                mapType.setValue(map.toString());
            } catch (IllegalArgumentException err) {
                System.out.println(err);
            }
        });
        setAction.put(Parameter.MUTATION_TYPE, value -> {
            try {
                MutationVariantName variant = MutationVariantName.valueOf(value);
                mutationVariant.setValue(variant.toString());
            } catch (IllegalArgumentException err) {
                System.out.println(err);
            }
        });
        setAction.put(Parameter.GENOTYPE_LENGTH, genotypeLengthInput::setText);
        setAction.put(Parameter.MAX_MUTATIONS, maxMutationsInput::setText);
        setAction.put(Parameter.MIN_MUTATIONS, minMutationsInput::setText);
        setAction.put(Parameter.STARTING_PLANTS, startingPlantsInput::setText);
        setAction.put(Parameter.STARTING_ANIMALS, startingAnimalsInput::setText);
        setAction.put(Parameter.MIN_REPRODUCTION_ENERGY, minReproductionEnergyInput::setText);
        setAction.put(Parameter.REPRODUCTION_ENERGY_COST, reproductionEnergyCostInput::setText);
        setAction.put(Parameter.STARTING_ENERGY, startingEnergyInput::setText);
        setAction.put(Parameter.DAILY_ENERGY_COST, dailyEnergyCostInput::setText);
        setAction.put(Parameter.DAILY_PLANTS_GROWTH, dailyPlantsGrowthInput::setText);
        setAction.put(Parameter.ENERGY_FROM_PLANT, energyFromPlantInput::setText);
        setAction.put(Parameter.SAVE_TO_CSV, value -> saveStatsToCSVCheckBox.setSelected(Boolean.parseBoolean(value)));
    }

    private void generateInputGetters() {
        getActions.put(Parameter.MAP_WIDTH, () -> mapWidth.getText());
        getActions.put(Parameter.MAP_HEIGHT, () -> mapHeight.getText());
        getActions.put(Parameter.MAP_TYPE, () -> mapType.getValue());
        getActions.put(Parameter.MUTATION_TYPE, () -> mutationVariant.getValue());
        getActions.put(Parameter.GENOTYPE_LENGTH, () -> genotypeLengthInput.getText());
        getActions.put(Parameter.MAX_MUTATIONS, () -> maxMutationsInput.getText());
        getActions.put(Parameter.MIN_MUTATIONS, () -> minMutationsInput.getText());
        getActions.put(Parameter.STARTING_PLANTS, () -> startingPlantsInput.getText());
        getActions.put(Parameter.STARTING_ANIMALS, () -> startingAnimalsInput.getText());
        getActions.put(Parameter.MIN_REPRODUCTION_ENERGY, () -> minReproductionEnergyInput.getText());
        getActions.put(Parameter.REPRODUCTION_ENERGY_COST, () -> reproductionEnergyCostInput.getText());
        getActions.put(Parameter.STARTING_ENERGY, () -> startingEnergyInput.getText());
        getActions.put(Parameter.DAILY_ENERGY_COST, () -> dailyEnergyCostInput.getText());
        getActions.put(Parameter.DAILY_PLANTS_GROWTH, () -> dailyPlantsGrowthInput.getText());
        getActions.put(Parameter.ENERGY_FROM_PLANT, () -> energyFromPlantInput.getText());
        getActions.put(Parameter.SAVE_TO_CSV, () -> String.valueOf(saveStatsToCSVCheckBox.isSelected()));
    }

}