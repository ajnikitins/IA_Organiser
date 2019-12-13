// TODO: https://stackoverflow.com/questions/36386579/javafx-titledpane-click-event-only-for-title

package com.zalktis.gui.components;

import com.zalktis.file.obj.Order;
import com.zalktis.file.obj.Task;
import java.util.function.Consumer;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;

public class ImminentTaskPane extends TitledPane {

  public ImminentTaskPane(Task task, Order order, Consumer<Order> onTitleClick) {
    super();

    HBox titleBox = new HBox();
    Label taskNameLabel = new Label(task.getName());
    Label taskDetailLabel = new Label(task.getDetails());

    Label orderNameLabel = new Label(order.getName());
    orderNameLabel.setStyle("-fx-underline: true;");

    orderNameLabel.setOnMouseClicked((e) -> onTitleClick.accept(order));

    titleBox.getChildren().addAll(taskNameLabel, orderNameLabel);

    setGraphic(titleBox);
    setContent(taskDetailLabel);
  }
}