package com.organiser.gui.components;

import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public abstract class AddDialog<T> extends Dialog<T> {

  private Button confirmButton;

  private GridPane grid;

  public AddDialog() {
    super();
    setupPreliminaryUI();
  }

  public Button getConfirmButton() {
    return confirmButton;
  }

  public GridPane getGrid() {
    return grid;
  }

  /**
   * Sets up the base UI to be later extended upon by subclasses.
   * Configures the confirmation and cancellation buttons - adds them to the dialog and sets their
   * images.
   * Creates an empty grid to be populated by subclasses.
   */
  private void setupPreliminaryUI() {
    // Add OK and Cancel buttons to the dialog.
    getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

    // Access the confirmation (OK) button and set its image.
    confirmButton = (Button) getDialogPane().lookupButton(ButtonType.OK);
    confirmButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("dialogConfirm.png"))));

    // Access the cancellation button and set its image.
    Button discardButton = (Button) getDialogPane().lookupButton(ButtonType.CANCEL);
    discardButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("dialogDiscard.png"))));

    // Create a basic, empty grid and configures some gaps between elements.
    grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
  }

  protected abstract void setupUI(String test);

  protected abstract void setupInputValidation();

  /**
   * Displays an alert of all validation errors concerning the inputs.
   * @param errors list of validation errors as strings
   */
  protected void showErrors(List<String> errors) {
    Alert alert = new Alert(AlertType.ERROR);

    alert.setTitle("Invalid inputs");
    alert.setHeaderText("Some inputs were found to be invalid");
    alert.setContentText(String.join("\n", errors));

    alert.showAndWait();
  }
}
