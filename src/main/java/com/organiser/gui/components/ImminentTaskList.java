package com.organiser.gui.components;

import com.organiser.file.obj.Order;
import com.organiser.file.obj.Task;
import java.util.function.Consumer;

/**
 * Implementation of {@link ObjectList} that displays the imminent task panes and their sub-elements.
 *
 * @see ImminentTaskList
 * @see com.organiser.gui.components.ImminentTaskPane
 * @see com.organiser.gui.components.ObjectList
 */
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
      getObjectAccordion().getPanes().add(new ImminentTaskPane(task, getFileSystem().findOrderByID(task.getParentID()), onTitleClick));
    }
  }
}
