package com.zalktis.gui.components;

import com.zalktis.file.obj.Order;
import com.zalktis.file.obj.Task;

public class TaskList extends ObjectList {

  private Order order;

  public TaskList(Order order) {
    super("taskList.fxml");

    this.order = order;
  }

  @Override
  protected void createObjectList() {
    for (Task task: order.getSortedTasks()) {
      getObjectAccordion().getPanes().add(new TaskPane(getFileSystem(), task));
    }
  }
}
