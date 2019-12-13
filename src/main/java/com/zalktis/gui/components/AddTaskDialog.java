// TODO: https://code.makery.ch/blog/javafx-dialogs-official/
// TODO: https://www.flaticon.com/free-icon/tick_447147?term=check%20mark&page=1&position=1
// TODO: https://www.flaticon.com/free-icon/delete_1214428?term=trash&page=1&position=3

package com.zalktis.gui.components;

import com.zalktis.file.obj.Order;
import com.zalktis.file.util.TimeMachine;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddTaskDialog extends AddDialog {

  private Order order;

  private TextField taskNameField;
  private Spinner<Integer> daysSpinner;
  private TextArea detailsArea;

  public AddTaskDialog(Order order) {
    super();

    this.order = order;

    setupUI();
    setupInputValidation();

    setResultConverter(dialogButtonType -> {
      if (dialogButtonType == ButtonType.OK) {
        List<String> result = new ArrayList<>();
        result.add(taskNameField.getText());
        result.add(daysSpinner.getValue().toString());
        result.add(detailsArea.getText());

        return result;
      }
      return null;
    });
  }

  public void setupUI() {
    setTitle("Add a new task");

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

  public void setupInputValidation() {
    getConfirmButton().addEventFilter(ActionEvent.ACTION, event -> {
      List<String> errors = new ArrayList<>();

      if (taskNameField.getText().length() == 0) {
        errors.add("A task name must be entered.");
      }

      if (TimeMachine.getDate(daysSpinner.getValue(), order.getCompletionDate()).isBefore(TimeMachine.now())) {
        errors.add("The days before completion date cannot be earlier than today.");
      }

      if (errors.size() > 0) {
        showErrors(errors);
        event.consume();
      }
    });
  }
}
