package agh.ics.oop.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class App extends Application {
    private static final int SIZE = 10;
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("views/startingView.fxml"));
        Scene scene = new Scene(loader.load(), 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
