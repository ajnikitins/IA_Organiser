package com.zalktis.gui.components;

import com.zalktis.file.util.TimeMachine;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ImminentTaskList extends VBox {

  @FXML
  private Accordion taskAccordion;

  @FXML
  private Label dateLabel;

  public ImminentTaskList() throws IOException {
    super();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("imminentTaskList.fxml"));
    loader.setController(this);
    loader.setRoot(this);
    loader.load();
  }

  @FXML
  public void initialize() {
    System.out.println(1);
    dateLabel.setText(TimeMachine.now().toString());
  }
}
