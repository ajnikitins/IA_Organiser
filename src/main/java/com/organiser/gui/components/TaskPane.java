// TODO: https://www.flaticon.com/free-icon/verified_907830?term=check%20mark&page=1&position=6
// TODO: https://www.flaticon.com/free-icon/edit_1159633?term=pencil&page=1&position=1

package com.organiser.gui.components;

import com.organiser.file.obj.Order;
import com.organiser.file.obj.Task;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class TaskPane extends TitledPane {

  private final Task task;
  private final Order order;

  public TaskPane(Order order, Task task) {
    this.task = task;
    this.order = order;

    TaskPaneTitle taskPaneTitle = new TaskPaneTitle();
    taskPaneTitle.prefWidthProperty().bind(widthProperty().subtract(50));
    setGraphic(taskPaneTitle);

    setContent(new Label(task.getDetails()));
  }

  private class TaskPaneTitle extends BorderPane {

    @FXML
    Label taskNameLabel;

    @FXML
    Label completionDateLabel;

    @FXML
    Label daysBeforeCompletionLabel;

    @FXML
    Button completeButton;

    @FXML
    Button editButton;

    public TaskPaneTitle() {
      super();

      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("taskPaneTitle.fxml"));
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
      completionDateLabel.setText(task.getCompletionDate().toString());
      daysBeforeCompletionLabel.setText("(" + task.getDaysBeforeOrder() + " days)");

      completeButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("paneComplete.png"))));
      editButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("paneEdit.png"))));
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
