package com.zalktis.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zalktis.file.obj.Order;
import com.zalktis.file.obj.Task;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileSystem implements Serializable {

  private static final String PATH_NAME = System.getProperty("user.home")  + "\\AppData\\Roaming\\Organiser\\save.json";

  private List<Order> orders;
  private List<Task> tasks;

  private FileSystem(List<Order> orders, List<Task> tasks) {
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

  @Override
  public String toString() {
    return "FileSystem{" +
        ", orders=" + orders +
        ", tasks=" + tasks +
        '}';
  }

  public static FileSystem load() {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.readValue(new File(PATH_NAME), FileSystem.class);
    } catch (IOException e) {
      System.out.println("Failed to read save file, loading blank.");
      e.printStackTrace();
      return new FileSystem(new ArrayList<>(), new ArrayList<>());
    }
  }

  public static void main(String[] args) {

  }

}
