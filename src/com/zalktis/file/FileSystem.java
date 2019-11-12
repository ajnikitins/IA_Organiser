//TODO: Write tests for this, Order, and Task

package com.zalktis.file;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zalktis.file.obj.Order;
import com.zalktis.file.obj.Task;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileSystem implements Serializable {

  private static final String PATH_NAME = System.getProperty("user.home")  + "\\AppData\\Roaming\\Organiser\\save.json";

  @JsonProperty("orders")
  private List<Order> orders;

  @JsonProperty("task")
  private List<Task> tasks;

  public FileSystem() {
    this.orders = new ArrayList<>();
    this.tasks = new ArrayList<>();
  }

  public FileSystem(List<Order> orders, List<Task> tasks) {
    this.orders = orders;
    this.tasks = tasks;
  }

  public List<Order> getOrders() {
    return orders;
  }

  public List<Task> getTasks() {
    return tasks;
  }

  public void addOrder(String name, String customerName, String details, LocalDate completionDate) {
    Order order = new Order(name, customerName, details, completionDate);
    addOrder(order);
  }

  public void addOrder(Order order) {
    order.setID(orders.size());
    orders.add(order);
    tasks.addAll(order.getTasks());
  }

  public static FileSystem load() {
    return InputOutputHandler.loadObject(PATH_NAME, FileSystem.class);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FileSystem system = (FileSystem) o;
    return orders.equals(system.orders) &&
        tasks.equals(system.tasks);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orders, tasks);
  }
}
