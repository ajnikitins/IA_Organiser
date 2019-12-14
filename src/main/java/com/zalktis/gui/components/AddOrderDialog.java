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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddOrderDialog extends AddDialog<Order> {

  private TextField orderNameField;
  private TextField customerNameField;
  private DatePicker completionDatePicker;
  private TextArea detailsArea;

  public AddOrderDialog() {
    super();

    setupUI();
    setupInputValidation();

    setResultConverter(dialogButtonType -> {
      if (dialogButtonType == ButtonType.OK) {
        return new Order(orderNameField.getText(), customerNameField.getText(), detailsArea.getText(), completionDatePicker.getValue());
      }
      return null;
    });
  }

  public void setupUI() {
    setTitle("Add a new order");

    orderNameField = new TextField();
    orderNameField.setPromptText("Order name");

    getGrid().add(new Label("Order name:"), 0, 0);
    getGrid().add(orderNameField, 1, 0);

    customerNameField = new TextField();
    customerNameField.setPromptText("Client name");

    getGrid().add(new Label("Customer name:"), 0, 1);
    getGrid().add(customerNameField, 1, 1);

    completionDatePicker = new DatePicker(TimeMachine.now());

    getGrid().add(new Label("Completion date:"), 0, 2);
    getGrid().add(completionDatePicker, 1, 2);

    detailsArea = new TextArea();
    detailsArea.setPromptText("Order details");

    getGrid().add(new Label("Order details:"), 0, 3);
    getGrid().add(detailsArea, 1, 3);

    getDialogPane().setContent(getGrid());
  }

  public void setupInputValidation() {
    getConfirmButton().addEventFilter(ActionEvent.ACTION, event -> {
      List<String> errors = new ArrayList<>();

      if (orderNameField.getText().length() == 0) {
        errors.add("An order name must be entered.");
      }

      if (customerNameField.getText().length() == 0) {
        errors.add("A customer name must be entered.");
      }

      if (completionDatePicker.getValue().isBefore(TimeMachine.now())) {
        errors.add("The due date cannot be earlier than today (" + TimeMachine.now() + ").");
      }

      if (TimeMachine.isHoliday(completionDatePicker.getValue())) {
        errors.add("The due date cannot be a holiday.");
      }

      if (errors.size() > 0) {
        showErrors(errors);
        event.consume();
      }
    });
  }


}
