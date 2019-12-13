package com.zalktis.gui.components;

import com.zalktis.file.FileSystem;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.layout.VBox;

public abstract class ObjectList extends VBox {

  @FXML
  private Accordion objectAccordion;

  private FileSystem fileSystem;

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

  public FileSystem getFileSystem() {
    return fileSystem;
  }

  public void setFileSystem(FileSystem fileSystem) {
    this.fileSystem = fileSystem;
  }

  public void update() {
    objectAccordion.getPanes().clear();
    createObjectList();
  }

  protected abstract void createObjectList();
}
