package com.zalktis.gui.components;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class TaskPaneTitle extends BorderPane {

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

  String taskName;
  String completionDate;
  int daysBeforeCompletion;
  Runnable onClickComplete;
  Runnable onClickEdit;

  public TaskPaneTitle(String taskName, String completionDate, int daysBeforeCompletion, Runnable onClickComplete, Runnable onClickEdit) {
    super();
    this.taskName = taskName;
    this.completionDate = completionDate;
    this.daysBeforeCompletion = daysBeforeCompletion;
    this.onClickComplete = onClickComplete;
    this.onClickEdit = onClickEdit;

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
    taskNameLabel.setText(taskName);
    completionDateLabel.setText(completionDate);
    daysBeforeCompletionLabel.setText("(" + daysBeforeCompletion + " days)");

    completeButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("paneComplete.png"))));
    editButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("paneEdit.png"))));
  }

  @FXML
  public void onClickCompleteButton() {
    onClickComplete.run();
  }

  @FXML
  public void onClickEditButton() {
    onClickEdit.run();
  }

}
