// TODO: https://stackoverflow.com/questions/9966136/javafx-periodic-background-task

package com.zalktis.gui.controllers;

import com.zalktis.file.FileSystem;
import com.zalktis.file.util.TimeMachine;
import com.zalktis.gui.components.ImminentTaskList;
import com.zalktis.gui.components.OrderList;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.util.Duration;

public class MainController implements Initializable {

  @FXML
  private TabPane mainPane;

  @FXML
  private ImminentTaskList imminentTaskList;

  @FXML
  private OrderList orderList;

  @FXML
  private Label dateLabel;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    dateLabel.setText(TimeMachine.now().toString());
    Timeline clock = new Timeline(new KeyFrame(Duration.minutes(10), (e) -> dateLabel.setText(TimeMachine.now().toString())));
    clock.setCycleCount(Timeline.INDEFINITE);
    clock.play();

    FileSystem fileSystem = FileSystem.load();
    fileSystem.setOnChange(() -> {
      imminentTaskList.update();
      orderList.update();
      fileSystem.save(fileSystem);
    });

    imminentTaskList.setOnTitleClick(order -> {
      mainPane.getSelectionModel().select(1);
      orderList.findOrderPaneByID(order.getID()).setExpanded(true);
    });

    imminentTaskList.setFileSystem(fileSystem);
    orderList.setFileSystem(fileSystem);

    mainPane.tabMinWidthProperty().bind(mainPane.widthProperty().divide(mainPane.getTabs().size()).subtract(20));
  }
}
