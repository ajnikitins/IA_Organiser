// TODO: https://stackoverflow.com/questions/9966136/javafx-periodic-background-task

package com.zalktis.gui.controllers;

import com.zalktis.file.FileSystem;
import com.zalktis.gui.components.ImminentTaskList;
import com.zalktis.gui.components.OrderList;
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

  @FXML
  private OrderList orderList;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    FileSystem fileSystem = FileSystem.load();
    fileSystem.setOnChange(() -> {
      imminentTaskList.update();
      orderList.update();
    });
    imminentTaskList.setFileSystem(fileSystem);
    orderList.setFileSystem(fileSystem);

    imminentTaskList.setOnTitleClick(order -> {
      mainPane.getSelectionModel().select(1);
      orderList.findOrderPaneByID(order.getID()).setExpanded(true);
    });

    mainPane.tabMinWidthProperty().bind(mainPane.widthProperty().divide(mainPane.getTabs().size()).subtract(20));
  }
}
