package com.zalktis.file.obj;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zalktis.file.util.LocalDateDeserializer;
import com.zalktis.file.util.LocalDateSerializer;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order implements Serializable {

  @JsonProperty("ID")
  private int ID;

  @JsonProperty("tasks")
  private List<Task> tasks;

  @JsonProperty("name")
  private String name;

  @JsonProperty("customerName")
  private String customerName;

  @JsonProperty("details")
  private String details;

  @JsonProperty("completionDate")
  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonSerialize(using = LocalDateSerializer.class)
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

  @JsonProperty("ID")
  public int getID() {
    return ID;
  }

  @JsonProperty("tasks")
  public List<Task> getTasks() {
    return tasks;
  }

  @JsonProperty("name")
  public String getName() {
    return name;
  }

  @JsonProperty("customerName")
  public String getCustomerName() {
    return customerName;
  }

  @JsonProperty("details")
  public String getDetails() {
    return details;
  }

  @JsonProperty("completionDate")
  public LocalDate getCompletionDate() {
    return completionDate;
  }

  @JsonProperty("ID")
  public void setID(int ID) {
    this.ID = ID;
  }

  @JsonProperty("name")
  public void setName(String name) {
    this.name = name;
  }

  @JsonProperty("customerName")
  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  @JsonProperty("details")
  public void setDetails(String details) {
    this.details = details;
  }

  @JsonProperty("completionDate")
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

    Task task = new Task(largestID + 1, ID, name, details, daysBefore, completionDate);
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Order order = (Order) o;
    return ID == order.ID &&
        tasks.equals(order.tasks) &&
        name.equals(order.name) &&
        customerName.equals(order.customerName) &&
        details.equals(order.details) &&
        completionDate.equals(order.completionDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ID, tasks, name, customerName, details, completionDate);
  }
}
