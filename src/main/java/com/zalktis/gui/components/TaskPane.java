// TODO: https://www.flaticon.com/free-icon/verified_907830?term=check%20mark&page=1&position=6
// TODO: https://www.flaticon.com/free-icon/edit_1159633?term=pencil&page=1&position=1

package com.zalktis.gui.components;

import com.zalktis.file.FileSystem;
import com.zalktis.file.obj.Task;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;

public class TaskPane extends TitledPane {

  public TaskPane(FileSystem fileSystem, Task task) {

    Runnable onClickComplete = () -> fileSystem.removeTask(task.getParentID(), task.getID());

    Runnable onClickEdit = () -> {
      // TODO: Finish this
    };

    setGraphic(new TaskPaneTitle(task.getName(), task.getCompletionDate().toString(), task.getDaysBeforeOrder(), onClickComplete, onClickEdit));

    setContent(new Label(task.getDetails()));
  }
}
