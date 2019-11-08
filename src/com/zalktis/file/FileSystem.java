package com.zalktis.file;

import com.zalktis.file.obj.Order;
import com.zalktis.file.obj.Task;
import java.io.Serializable;
import java.util.List;

public class FileSystem implements Serializable {

  public static final String PATH_NAME = "C:\\Users\\artur\\AppData\\Roaming\\Organiser\\save";

  private String filename;
  private List<Order> orders;
  private List<Task> tasks;

  private FileSystem(String filename, List<Order> orders, List<Task> tasks) {
    this.filename = filename;
    this.orders = orders;
    this.tasks = tasks;
  }

  @Override
  public String toString() {
    return "FileSystem{" +
        "filename='" + filename + '\'' +
        ", orders=" + orders +
        ", tasks=" + tasks +
        '}';
  }

  public static void main(String[] args) {

  }

}
