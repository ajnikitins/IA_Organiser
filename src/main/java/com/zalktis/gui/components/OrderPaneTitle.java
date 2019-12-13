package com.zalktis.gui.components;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class OrderPaneTitle extends HBox {

  @FXML
  Label orderNameLabel;

  @FXML
  Label completionDateLabel;

  @FXML
  Button completeButton;

  @FXML
  Button editButton;

  String orderName;
  String completionDate;
  Runnable onClickComplete;
  Runnable onClickEdit;

  public OrderPaneTitle(String orderName, String completionDate, Runnable onClickComplete, Runnable onClickEdit) {
    super();
    this.orderName = orderName;
    this.completionDate = completionDate;
    this.onClickComplete = onClickComplete;
    this.onClickEdit = onClickEdit;

    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("orderPaneTitle.fxml"));
      loader.setController(this);
      loader.setRoot(this);
      loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void initialize() {
    orderNameLabel.setText(orderName);
    completionDateLabel.setText(completionDate);

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
