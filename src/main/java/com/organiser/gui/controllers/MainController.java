// TODO: https://stackoverflow.com/questions/9966136/javafx-periodic-background-task

package com.organiser.gui.controllers;

import com.organiser.file.Filesystem;
import com.organiser.file.util.TimeMachine;
import com.organiser.gui.components.ImminentTaskList;
import com.organiser.gui.components.OrderList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.util.Duration;

/**
 * Main controller class which contains the top level components.
 */
public class MainController {

  /**
   * Tab pane that contains the imminent task list and the order list.
   *
   * @see ImminentTaskList
   * @see OrderList
   * @see TabPane
   */
  @FXML
  private TabPane mainPane;

  @FXML
  private ImminentTaskList imminentTaskList;

  @FXML
  private OrderList orderList;

  /**
   * Label displaying the current date; it is refreshed on a 10-minute basis.
   */
  @FXML
  private Label dateLabel;

  /**
   * Called to initialize a controller after its root element has been completely processed.
   * The method sets up the refreshing of dateLabel,
   * loads the filesystem and passes its reference to imminentTaskList and objectList,
   * sets the callback for when a title of an imminent task pane is clicked on,
   * auto scales the tabs to equal size.
   *
   * @see Filesystem
   * @see ImminentTaskList
   * @see com.organiser.gui.components.ImminentTaskPane
   * @see OrderList
   */
  public void initialize() {

    // dateLabel refreshing
    dateLabel.setText(TimeMachine.now().toString());
    Timeline clock = new Timeline(
        new KeyFrame(Duration.minutes(10), (e) -> dateLabel.setText(TimeMachine.now().toString())));
    clock.setCycleCount(Timeline.INDEFINITE);
    clock.play();

    // loading Filesystem
    Filesystem fileSystem = Filesystem.load();
    fileSystem.setOnChange(() -> {
      imminentTaskList.update();
      orderList.update();
      fileSystem.save();
    });

    // title click callback
    imminentTaskList.setOnTitleClick(order -> {
      mainPane.getSelectionModel().select(1);
      orderList.findOrderPaneByID(order.getID()).setExpanded(true);
    });

    imminentTaskList.setFileSystem(fileSystem);
    orderList.setFileSystem(fileSystem);

    // tab auto scaling
    mainPane.tabMinWidthProperty()
        .bind(mainPane.widthProperty().divide(mainPane.getTabs().size()).subtract(20));
  }
}
