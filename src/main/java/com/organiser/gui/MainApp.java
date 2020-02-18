package com.organiser.gui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

  public static void run(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("scene.fxml"));

    Scene scene = new Scene(root);
    scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

    primaryStage.setTitle("Organiser");
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
