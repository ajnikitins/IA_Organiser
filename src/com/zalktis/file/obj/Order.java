package com.zalktis.file.obj;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {
  private int ID;
  private List<Task> tasks;
  private String name;
  private String customerName;
  private String details;
  private LocalDate completionDate;

  public Order(int ID, List<Task> tasks, String name, String customerName, String details, LocalDate completionDate) {
    this.ID = ID;
    this.tasks = tasks;
    this.name = name;
    this.customerName = customerName;
    this.details = details;
    this.completionDate = completionDate;
  }

  public Order(List<Task> tasks, String name, String customerName, String details, LocalDate completionDate) {
    this.tasks = tasks;
    this.name = name;
    this.customerName = customerName;
    this.details = details;
    this.completionDate = completionDate;
  }

  public Order(String name, String customerName, String details, LocalDate completionDate) {
    this.tasks = new ArrayList<>();
    this.name = name;
    this.customerName = customerName;
    this.details = details;
    this.completionDate = completionDate;
  }

  public int getID() {
    return ID;
  }

  public List<Task> getTasks() {
    return tasks;
  }

  public String getName() {
    return name;
  }

  public String getCustomerName() {
    return customerName;
  }

  public String getDetails() {
    return details;
  }

  public LocalDate getCompletionDate() {
    return completionDate;
  }

  public void setID(int ID) {
    this.ID = ID;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public void setCompletionDate(LocalDate completionDate) {
    this.completionDate = completionDate;
  }

  public void addTask(String name, String details, int daysBefore) {
    // Find largest ID
    int largestID = -1;
    for (Task task : tasks) {
      if (task.getID() > largestID) {
        largestID = task.getID();
      }
    }

    // Create new Task
    Task task = new Task(largestID + 1, this, name, details, daysBefore);
    tasks.add(task);
  }

  public Task findTaskByID(int ID) {
    for(Task task : tasks) {
      if(task.getID() == ID) {
        return task;
      }
    }
    return null;
  }

  public void removeTask(int ID) {
    Task task = findTaskByID(ID);
    tasks.remove(task);
  }
}
