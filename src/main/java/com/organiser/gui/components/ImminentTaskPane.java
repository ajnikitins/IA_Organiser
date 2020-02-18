// TODO: https://stackoverflow.com/questions/36386579/javafx-titledpane-click-event-only-for-title

package com.organiser.gui.components;

import com.organiser.file.obj.Order;
import com.organiser.file.obj.Task;
import java.util.function.Consumer;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;

public class ImminentTaskPane extends TitledPane {

  public ImminentTaskPane(Task task, Order order, Consumer<Order> onTitleClick) {
    super();

    BorderPane titleBox = new BorderPane();
    Label taskNameLabel = new Label(task.getName());
    Label taskDetailLabel = new Label(task.getDetails());

    Label orderNameLabel = new Label(order.getName());
    orderNameLabel.setStyle("-fx-underline: true;");

    orderNameLabel.setOnMouseClicked((e) -> onTitleClick.accept(order));

    titleBox.setLeft(taskNameLabel);
    titleBox.setCenter(orderNameLabel);

    titleBox.prefWidthProperty().bind(widthProperty().subtract(34));

    setGraphic(titleBox);
    setContent(taskDetailLabel);
  }
}
