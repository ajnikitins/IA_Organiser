package com.organiser.file;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.organiser.file.obj.Order;
import com.organiser.file.obj.Task;
import com.organiser.file.util.TimeMachine;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class FileSystem {

  private static final String PATH_NAME = System.getProperty("user.home")  + "\\AppData\\Roaming\\Organiser\\save.json";

  @JsonProperty("orders")
  private final Map<Integer, Order> orders;

  @JsonProperty("currentID")
  private int currentID = 0;

  @JsonIgnore
  private Runnable onChange;

  public FileSystem() {
    this.orders = new TreeMap<>();
    this.onChange = () -> {};
  }

  @JsonProperty("orders")
  public Map<Integer, Order> getOrders() {
    return orders;
  }

  @JsonIgnore
  public Runnable getOnChange() {
    return onChange;
  }

  @JsonIgnore
  public void setOnChange(Runnable onChange) {
    this.onChange = onChange;
  }

  @JsonIgnore
  public List<Task> getImminentTasks() {
    List<Task> tasks = new LinkedList<>();

    for (Order order : orders.values()) {
      for (Task task : order.getTasks().values()) {
        if (TimeMachine.now().isEqual(task.getCompletionDate())) {
          tasks.add(task);
        }
      }
    }

    return tasks;
  }

  @JsonIgnore
  public List<Order> getSortedOrders() {
    return orders.values().stream().sorted(Comparator.comparing(Order::getCompletionDate)).collect(Collectors.toList());
  }

  public void addOrder(Order order) {
    order.setID(currentID);
    orders.put(currentID, order);
    currentID++;

    onChange.run();
  }

  public Order findOrderByID(int ID) {
    return orders.get(ID);
  }

  public void removeOrder(int ID) {
    orders.remove(ID);

    onChange.run();
  }

  public static FileSystem load() {
    return IOHandler.loadObject(PATH_NAME, FileSystem.class);
  }

  public void save() {
    IOHandler.writeObject(PATH_NAME, this);
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
}
