package com.zalktis.file;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zalktis.file.obj.Order;
import com.zalktis.file.obj.Task;
import com.zalktis.file.util.TimeMachine;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileSystem {

  private static final String PATH_NAME = System.getProperty("user.home")  + "\\AppData\\Roaming\\Organiser\\save.json";

  @JsonProperty("orders")
  private List<Order> orders;

  @JsonIgnore
  private Runnable onChange;

  public FileSystem() {
    this.orders = new ArrayList<>();
  }

  public FileSystem(List<Order> orders) {
    this.orders = orders;
  }

  public List<Order> getOrders() {
    return orders;
  }

  public Runnable getOnChange() {
    return onChange;
  }

  public void setOnChange(Runnable onChange) {
    this.onChange = onChange;
  }

  public int currentID = 0;

  public List<Task> getTasks() {
    ArrayList<Task> tasks = new ArrayList<>();

    for (Order order : orders) {
      tasks.addAll(order.getTasks());
    }

    return tasks;
  }

  public List<Task> getImminentTasks() {
    ArrayList<Task> tasks = new ArrayList<>();

    for (Order order : orders) {
      for (Task task : order.getTasks()) {
        if (TimeMachine.now().isEqual(task.getCompletionDate())) {
          tasks.add(task);
        }
      }
    }

    return tasks;
  }

  public List<Order> getSortedOrders() {
    return orders.stream().sorted(Comparator.comparing(Order::getCompletionDate)).collect(Collectors.toList());
  }

  public void addOrder(Order order) {
    order.setID(currentID++);
    orders.add(order);

    onChange.run();
  }

  @Deprecated
  public void addOrder(String name, String customerName, String details, LocalDate completionDate) {
    addOrder(new Order(name, customerName, details, completionDate));
  }

  public void addTask(int parentID, Task task) {
    findOrderByID(parentID).addTask(task);
    onChange.run();
  }

  @Deprecated
  public void addTask(int parentID, String name, String details, int daysBefore) {
    addTask(parentID, new Task(name, details, daysBefore));
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

  public void updateOrder(Order order) {
    for (int i = 0; i < orders.size(); i++) {
      if (orders.get(i).getID() == order.getID()) {
        orders.set(i, order);
      }
    }

    onChange.run();
  }

  public void updateTask(Task task) {
    Order order = findOrderByID(task.getParentID());

    if (order != null) {
      order.updateTask(task);
    }

    onChange.run();
  }

  public void removeOrder(int ID) {
    orders.remove(findOrderByID(ID));

    onChange.run();
  }

  public void removeTask(int orderID, int taskID) {
    Order order = findOrderByID(orderID);

    if (order != null) {
      order.removeTask(taskID);
    }

    onChange.run();
  }

  public static FileSystem load() {
    return IOHandler.loadObject(PATH_NAME, FileSystem.class);
  }

  public void save(FileSystem fileSystem) {
    IOHandler.writeObject(PATH_NAME, fileSystem);
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
