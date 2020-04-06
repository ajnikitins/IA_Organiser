// https://code.makery.ch/blog/javafx-dialogs-official/
// https://www.flaticon.com/free-icon/tick_447147
// https://www.flaticon.com/free-icon/delete_1214428

package com.organiser.gui.components;

import com.organiser.file.obj.Order;
import com.organiser.file.obj.Task;
import java.util.LinkedList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddTaskDialog extends AddDialog<Task> {

  private final Order order;

  private TextField taskNameField;
  private Spinner<Integer> daysSpinner;
  private TextArea detailsArea;

  public AddTaskDialog(Order order) {
    super();

    this.order = order;

    setupUI("Add a new task");
    setupInputValidation();

    setResultConverter(dialogButtonType -> {
      if (dialogButtonType == ButtonType.OK) {
        return new Task(taskNameField.getText(), detailsArea.getText(), daysSpinner.getValue());
      }
      return null;
    });
  }

  public AddTaskDialog(Order order, Task task) {
    this(order);

    setTitle("Edit a task");

    taskNameField.setText(task.getName());
    daysSpinner.getValueFactory().setValue(task.getDaysBeforeOrder());
    detailsArea.setText(task.getDetails());

    setResultConverter(dialogButtonType -> {
      if(dialogButtonType == ButtonType.OK) {
        task.setName(taskNameField.getText());
        task.setDaysBeforeOrder(daysSpinner.getValue());
        task.setDetails(detailsArea.getText());
      }
      return null;
    });
  }

  @Override
  public void setupUI(String title) {
    setTitle(title);

    taskNameField = new TextField();
    taskNameField.setPromptText("Task name");

    getGrid().add(new Label("Task name:"), 0, 0);
    getGrid().add(taskNameField, 1, 0);

    daysSpinner = new Spinner<>();
    daysSpinner.setValueFactory(new IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0));

    getGrid().add(new Label("Days before completion:"), 0, 2);
    getGrid().add(daysSpinner, 1, 2);

    detailsArea = new TextArea();
    detailsArea.setPromptText("Task details");

    getGrid().add(new Label("Task details:"), 0, 3);
    getGrid().add(detailsArea, 1, 3);

    getDialogPane().setContent(getGrid());
  }

  @Override
  protected void setupInputValidation() {
    getConfirmButton().addEventFilter(ActionEvent.ACTION, event -> {
      boolean[] errors = Task.validateProperties(taskNameField.getText(), daysSpinner.getValue(), order.getCompletionDate());
      List<String> errorMessages = new LinkedList<>();

      if (errors[0]) {
        errorMessages.add("A task name must be entered.");
      }

      if (errors[1]) {
        errorMessages.add("The days before completion date cannot be earlier than today.");
      }

      if (errorMessages.size() > 0) {
        showErrors(errorMessages);
        event.consume();
      }
    });
  }
}
