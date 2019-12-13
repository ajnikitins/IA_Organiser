package com.zalktis.gui.components;

import com.zalktis.file.obj.Order;
import com.zalktis.file.obj.Task;
import java.util.function.Consumer;

public class ImminentTaskList extends ObjectList {

  Consumer<Order> onTitleClick;

  public ImminentTaskList() {
    super("imminentTaskList.fxml");
  }

  public void setOnTitleClick(Consumer<Order> onTitleClick) {
    this.onTitleClick = onTitleClick;
  }

  @Override
  public void createObjectList() {
    for (Task task : getFileSystem().getImminentTasks()) {
      getObjectAccordion().getPanes().add(new ImminentTaskPane(task, getFileSystem().findOrderByID(task.getParentID()),
          onTitleClick));
    }
  }
}
