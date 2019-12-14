package com.zalktis.gui.components;

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

  private void setupPreliminaryUI() {
    getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

    confirmButton = (Button) getDialogPane().lookupButton(ButtonType.OK);
    confirmButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("dialogConfirm.png"))));

    Button discardButton = (Button) getDialogPane().lookupButton(ButtonType.CANCEL);
    discardButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("dialogDiscard.png"))));

    grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
  }

  protected void showErrors(List<String> errors) {
    Alert alert = new Alert(AlertType.ERROR);

    alert.setTitle("Invalid inputs");
    alert.setHeaderText("Some inputs were found to be invalid");
    alert.setContentText(String.join("\n", errors));

    alert.showAndWait();
  }
}
