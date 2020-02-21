// TODO: https://code.makery.ch/blog/javafx-dialogs-official/
// TODO: https://www.flaticon.com/free-icon/tick_447147
// TODO: https://www.flaticon.com/free-icon/delete_1214428

package com.organiser.gui.components;

import com.organiser.file.obj.Order;
import com.organiser.file.util.TimeMachine;
import java.util.LinkedList;
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

  /**
   * Constructor for creating a new order.
   */
  public AddOrderDialog() {
    super();

    setupUI("Add a new order");
    setupInputValidation();

    /*
    Configures what is output by the dialog when a button is pressed or it closes.
    If the confirm button is pressed and the inputs are valid, then a new Order
    object is created and returned.
    Otherwise null is returned.
     */
    setResultConverter(dialogButtonType -> {
      if (dialogButtonType == ButtonType.OK) {
        return new Order(orderNameField.getText(), customerNameField.getText(),
            detailsArea.getText(), completionDatePicker.getValue());
      }
      return null;
    });
  }

  /**
   * Constructor for editing an already existing order.
   * @param order pre-existing Order object to be edited
   */
  public AddOrderDialog(Order order) {
    super();

    setupUI("Edit an order");
    setupInputValidation();

    orderNameField.setText(order.getName());
    customerNameField.setText(order.getCustomerName());
    completionDatePicker.setValue(order.getCompletionDate());
    detailsArea.setText(order.getDetails());

    /*
    Configures what is output by the dialog when a button is pressed or it closes.
    If the confirm button is pressed and the inputs are valid, then the Order
    object's fields are set to the values from the text fields.
    Otherwise null is returned.
     */
    setResultConverter(dialogButtonType -> {
      if (dialogButtonType == ButtonType.OK) {
        order.setName(orderNameField.getText());
        order.setCustomerName(customerNameField.getText());
        order.setCompletionDate(completionDatePicker.getValue());
        order.setDetails(detailsArea.getText());

        return order;
      }
      return null;
    });
  }

  /**
   * Sets up UI for Order values.
   * @param title Title for the dialog.
   */
  @Override
  protected void setupUI(String title) {
    setTitle(title);

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

  /**
   * Adds a callback to when the confirm button is pressed which
   * calls the validation method for Orders and then defines the shown messages.
   * If there are validation errors, then they are displayed to the user
   * and further callbacks are stopped to prevent the dialog from closing.
   * Otherwise, nothing happens and the dialog closes.
   */
  @Override
  protected void setupInputValidation() {
    getConfirmButton().addEventFilter(ActionEvent.ACTION, event -> {
      boolean[] errors = Order.validateProperties(orderNameField.getText(), customerNameField.getText(), completionDatePicker.getValue());
      List<String> errorMessages = new LinkedList<>();
      
      if (errors[0]) {
        errorMessages.add("An order name must be entered.");
      }

      if (errors[1]) {
        errorMessages.add("A customer name must be entered.");
      }

      if (errors[2]) {
        errorMessages.add("The due date cannot be earlier than today (" + TimeMachine.now() + ").");
      }

      if (errors[3]) {
        errorMessages.add("The due date cannot be a holiday.");
      }


      if (errorMessages.size() > 0) {
        showErrors(errorMessages);
        event.consume();
      }
    });
  }
}
