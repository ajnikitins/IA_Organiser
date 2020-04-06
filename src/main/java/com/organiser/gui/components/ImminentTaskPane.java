// https://stackoverflow.com/questions/36386579/javafx-titledpane-click-event-only-for-title

package com.organiser.gui.components;

import com.organiser.file.obj.Order;
import com.organiser.file.obj.Task;
import java.io.IOException;
import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class ImminentTaskPane extends TitledPane {

  private final Task task;
  private final Order order;
  private final Consumer<Order> onTitleClick;

  public ImminentTaskPane(Task task, Order order, Consumer<Order> onTitleClick) {
    super();

    this.task = task;
    this.order = order;
    this.onTitleClick = onTitleClick;

    var imminentTaskPaneTitle = new ImminentTaskPaneTitle();
    imminentTaskPaneTitle.prefWidthProperty().bind(widthProperty().subtract(34));
    setGraphic(imminentTaskPaneTitle);

    setContent(new Label(task.getDetails() + "\nOrder details: " + order.getDetails()));
  }

  private class ImminentTaskPaneTitle extends BorderPane {

    @FXML
    Label taskNameLabel;

    @FXML
    Label orderNameLabel;

    @FXML
    Button completeButton;

    @FXML
    Button editButton;

    public ImminentTaskPaneTitle() {
      super();

      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("imminentTaskPaneTitle.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        loader.load();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    @FXML
    public void initialize() {
      taskNameLabel.setText(task.getName());
      orderNameLabel.setText(order.getName());

      completeButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("paneComplete.png"))));
      editButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("paneEdit.png"))));
    }

    @FXML
    public void onClickOrderNameLabel() {
      onTitleClick.accept(order);
    }

    @FXML
    public void onClickCompleteButton() {
      order.removeTask(task.getID());
    }

    @FXML
    public void onClickEditButton() {
      new AddTaskDialog(order, task).showAndWait();
      order.getOnChange().run();
    }
  }
}
