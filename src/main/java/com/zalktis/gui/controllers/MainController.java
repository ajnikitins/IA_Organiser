package com.zalktis.gui.controllers;

import com.zalktis.gui.components.ImminentTaskList;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;

public class MainController implements Initializable {

  @FXML
  private TabPane mainPane;

  @FXML
  private ImminentTaskList imminentTaskList;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    mainPane.tabMinWidthProperty().bind(mainPane.widthProperty().divide(mainPane.getTabs().size()).subtract(20));
  }
}
