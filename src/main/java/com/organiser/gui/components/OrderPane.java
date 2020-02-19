// TODO: https://www.flaticon.com/free-icon/verified_907830?term=check%20mark&page=1&position=6
// TODO: https://www.flaticon.com/free-icon/edit_1159633?term=pencil&page=1&position=1

package com.organiser.gui.components;

import com.organiser.file.Filesystem;
import com.organiser.file.obj.Order;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class OrderPane extends TitledPane {

  private final Order order;
  private final Filesystem fileSystem;

  public OrderPane(Filesystem fileSystem, Order order) {
    this.order = order;
    this.fileSystem = fileSystem;

    order.setOnChange(fileSystem.getOnChange());

    OrderPaneTitle orderPaneTitle = new OrderPaneTitle();
    orderPaneTitle.prefWidthProperty().bind(widthProperty().subtract(35));
    setGraphic(orderPaneTitle);

    Button addTaskButton = new Button("Add Task");
    addTaskButton.setOnAction((e) -> new AddTaskDialog(order).showAndWait().ifPresent(order::addTask));

    TaskList taskList = new TaskList(order);
    taskList.setFileSystem(fileSystem);
    taskList.update();

    setContent(new VBox(new Label("Customer: " + order.getCustomerName()), new Label("Details: " + order.getDetails()), taskList, addTaskButton));
  }

  public Order getOrder() {
    return order;
  }

  private class OrderPaneTitle extends BorderPane {

    @FXML
    Label orderNameLabel;

    @FXML
    Label completionDateLabel;

    @FXML
    Button completeButton;

    @FXML
    Button editButton;

    public OrderPaneTitle() {
      super();

      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("orderPaneTitle.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        loader.load();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    @FXML
    public void initialize() {
      orderNameLabel.setText(order.getName());
      completionDateLabel.setText(order.getCompletionDate().toString());

      completeButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("paneComplete.png"))));
      editButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("paneEdit.png"))));
    }

    @FXML
    public void onClickCompleteButton() {
      fileSystem.removeOrder(order.getID());
    }

    @FXML
    public void onClickEditButton() {
      new AddOrderDialog(order).showAndWait();
      fileSystem.getOnChange().run();
    }

  }
}
