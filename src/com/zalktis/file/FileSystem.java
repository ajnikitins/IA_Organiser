//TODO: Write tests for this, Order, and Task

package com.zalktis.file;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zalktis.file.obj.Order;
import com.zalktis.file.obj.Task;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileSystem {

  private static final String PATH_NAME = System.getProperty("user.home")  + "\\AppData\\Roaming\\Organiser\\save.json";

  @JsonProperty("orders")
  private List<Order> orders;

  public FileSystem() {
    this.orders = new ArrayList<>();
  }

  public FileSystem(List<Order> orders) {
    this.orders = orders;
  }

  public List<Order> getOrders() {
    return orders;
  }

  public List<Task> getTasks() {
    ArrayList<Task> tasks = new ArrayList<>();

    for (Order order : orders) {
      tasks.addAll(order.getTasks());
    }

    return tasks;
  }

  public void addOrder(String name, String customerName, String details, LocalDate completionDate) {
    Order order = new Order(name, customerName, details, completionDate);
    addOrder(order);
  }

  public void addOrder(Order newOrder) {
    int largestID = -1;
    for (Order order : orders) {
      if (order.getID() > largestID) {
        largestID = order.getID();
      }
    }
    newOrder.setID(largestID + 1);
    orders.add(newOrder);
  }

  public void addTask(Order order, String name, String details, int daysBefore) {
    order.addTask(name, details, daysBefore);
  }

  public Order findOrderByID(int ID) {
    for(Order order : orders) {
      if (order.getID() == ID) {
        return order;
      }
    }
    return null;
  }

  public Task findTaskByID(int orderID, int taskID) {
    Order order = findOrderByID(orderID);

    return order == null ? null : order.findTaskByID(taskID);
  }

  public void removeOrder(int ID) {
    orders.remove(findOrderByID(ID));
  }

  public void removeTask(int orderID, int taskID) {
    Order order = findOrderByID(orderID);

    if (order != null) {
      order.removeTask(taskID);
    }
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
    return orders.equals(system.orders);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orders);
  }
}
