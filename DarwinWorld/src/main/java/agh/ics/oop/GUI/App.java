package agh.ics.oop.GUI;

import agh.ics.oop.GUI.controllers.StartingView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("views/startingView.fxml"));
        Scene scene = new Scene(loader.load(), 700, 600);
        scene.getStylesheets().add("views/startingView.css");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("/images/icon.png"));
        // Adding an icon to the Dock on a macOS
        if (Taskbar.isTaskbarSupported()) {
            var taskbar = Taskbar.getTaskbar();
            if (taskbar.isSupported(Taskbar.Feature.ICON_IMAGE)) {
                final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
                var dockIcon = defaultToolkit.getImage(getClass().getResource("/images/icon.png"));
                taskbar.setIconImage(dockIcon);
            }
        }
        primaryStage.show();
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Darwin World");
        StartingView view = loader.getController();
        view.init();
    }
}
