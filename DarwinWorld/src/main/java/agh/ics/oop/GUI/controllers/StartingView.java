package agh.ics.oop.GUI.controllers;

import agh.ics.oop.Simulation;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartingView {

    public void onSimulationStartClicked() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("views/simulationView.fxml"));
        Scene scene = new Scene(loader.load(), 1000, 800);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        SimulationView simulationController = loader.getController();

        simulationController.init();
    }
}
