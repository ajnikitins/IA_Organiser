package com.organiser.gui.components;

import com.organiser.file.Filesystem;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.layout.VBox;

public abstract class ObjectList extends VBox {

  @FXML
  private Accordion objectAccordion;

  private Filesystem fileSystem;

  public ObjectList(String fxmlName) {
    super();

    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlName));
      loader.setController(this);
      loader.setRoot(this);
      loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Accordion getObjectAccordion() {
    return objectAccordion;
  }

  public Filesystem getFileSystem() {
    return fileSystem;
  }

  public void setFileSystem(Filesystem fileSystem) {
    this.fileSystem = fileSystem;
    update();
  }

  public void update() {
    objectAccordion.getPanes().clear();
    createObjectList();
  }

  protected abstract void createObjectList();
}
