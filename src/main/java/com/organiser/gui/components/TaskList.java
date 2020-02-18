package com.organiser.gui.components;

import com.organiser.file.obj.Order;
import com.organiser.file.obj.Task;

public class TaskList extends ObjectList {

  private final Order order;

  public TaskList(Order order) {
    super("taskList.fxml");

    this.order = order;
  }

  @Override
  protected void createObjectList() {
    for (Task task: order.getSortedTasks()) {
      getObjectAccordion().getPanes().add(new TaskPane(order, task));
    }
  }
}
