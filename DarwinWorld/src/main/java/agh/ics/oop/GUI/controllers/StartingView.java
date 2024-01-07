package agh.ics.oop.GUI.controllers;

import agh.ics.oop.SimulationConfig;
import agh.ics.oop.model.Config.Parameter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.HashMap;

public class StartingView {
    @FXML private AnchorPane rootPane;
    @FXML private TextField mapWidth;
    @FXML private TextField mapHeight;
    @FXML private TextField genotypeLengthInput;
    @FXML private TextField minReproductionEnergyInput;
    @FXML private TextField reproductionEnergyCostInput;
    @FXML private TextField startingEnergyInput;
    @FXML private TextField dailyEnergyCostInput;
    @FXML private TextField dailyPlantsGrowthInput;
    @FXML private TextField energyFromPlantInput;

    public void init() {
        Image backgroundImage = new Image("/images/background.jpg");
        BackgroundImage background = new BackgroundImage(backgroundImage,
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        rootPane.setBackground(new Background(background));
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
        HashMap<Parameter, Integer> params = new HashMap<>();
        try {
            int width = Integer.parseInt(mapWidth.getText());
            params.put(Parameter.MAP_WIDTH, width);
            int height = Integer.parseInt(mapHeight.getText());
            params.put(Parameter.MAP_HEIGHT, height);
            int genotypeLength = Integer.parseInt(genotypeLengthInput.getText());
            params.put(Parameter.GENOTYPE_LENGTH, genotypeLength);
            int minReproductionEnergy = Integer.parseInt(minReproductionEnergyInput.getText());
            params.put(Parameter.MIN_REPRODUCTION_ENERGY, minReproductionEnergy);
            int reproductionEnergyCost = Integer.parseInt(reproductionEnergyCostInput.getText());
            params.put(Parameter.REPRODUCTION_ENERGY_COST, reproductionEnergyCost);
            int startingEnergy = Integer.parseInt(startingEnergyInput.getText());
            params.put(Parameter.STARTING_ENERGY, startingEnergy);
            int dailyEnergyCost = Integer.parseInt(dailyEnergyCostInput.getText());
            params.put(Parameter.DAILY_ENERGY_COST, dailyEnergyCost);
            int dailyPlantsGrowth = Integer.parseInt(dailyPlantsGrowthInput.getText());
            params.put(Parameter.DAILY_PLANTS_GROWTH, dailyPlantsGrowth);
            int energyFromPlant = Integer.parseInt(energyFromPlantInput.getText());
            params.put(Parameter.ENERGY_FROM_PLANT, energyFromPlant);
        } catch (NumberFormatException e) {
            System.out.println("Proszę wprowadzić prawidłowe liczby całkowite.");
        }
        return new SimulationConfig(params);
    }
}