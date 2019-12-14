// TODO: https://www.flaticon.com/free-icon/verified_907830?term=check%20mark&page=1&position=6
// TODO: https://www.flaticon.com/free-icon/edit_1159633?term=pencil&page=1&position=1

package com.zalktis.gui.components;

import com.zalktis.file.FileSystem;
import com.zalktis.file.obj.Order;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

public class OrderPane extends TitledPane {

  private Order order;

  public OrderPane(FileSystem fileSystem, Order order) {
    this.order = order;

    Runnable onClickComplete = () -> fileSystem.removeOrder(order.getID());

    Runnable onClickEdit = () -> {
      // TODO: Finish this
    };

    setGraphic(new OrderPaneTitle(order.getName(), order.getCompletionDate().toString(), onClickComplete, onClickEdit));

    Button addTaskButton = new Button("Add Task");
    addTaskButton.setOnAction((e) -> new AddTaskDialog(order).showAndWait().ifPresent((result) -> fileSystem.addTask(order.getID(), result)));

    TaskList taskList = new TaskList(order);
    taskList.setFileSystem(fileSystem);
    taskList.update();

    setContent(new VBox(new Label("Customer: " + order.getCustomerName()), new Label("Details: " + order.getDetails()), taskList, addTaskButton));
  }

  public Order getOrder() {
    return order;
  }
}
