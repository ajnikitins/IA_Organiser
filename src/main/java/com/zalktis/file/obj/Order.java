package com.zalktis.file.obj;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zalktis.file.exceptions.DateBeforeTodayException;
import com.zalktis.file.exceptions.HolidayException;
import com.zalktis.file.util.LocalDateDeserializer;
import com.zalktis.file.util.LocalDateSerializer;
import com.zalktis.file.util.TimeMachine;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Order {

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

  private int currentID = 0;

  public Order() {
    this.tasks = new ArrayList<>();
  }

  public Order(String name, String customerName, String details, LocalDate completionDate) {
    this();
    setName(name);
    setCustomerName(customerName);
    setDetails(details);
    setCompletionDate(completionDate);
  }

  @Deprecated
  public Order(int ID, String name, String customerName, String details, LocalDate completionDate) {
    this(name, customerName, details, completionDate);
    this.ID = ID;
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
    if (completionDate.isBefore(TimeMachine.now())) {
      throw new DateBeforeTodayException(String.format(DateBeforeTodayException.DEFAULT_MESSAGE, completionDate));
    } else if (TimeMachine.isHoliday(completionDate)) {
      throw new HolidayException(String.format(HolidayException.DEFAULT_MESSAGE, completionDate));
    } else {
      this.completionDate = completionDate;
      for (Task task: tasks) {
        task.setOrderCompletionDate(completionDate);
      }
    }
  }

  @Deprecated
  public void addTask(String name, String details, int daysBefore) {
    addTask(new Task(name, details, daysBefore));
  }

  public void addTask(Task task) {
    task.setID(currentID++);
    task.setParentID(ID);
    task.setOrderCompletionDate(completionDate);

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

  public List<Task> getSortedTasks() {
    return tasks.stream().sorted(Comparator.comparing(Task::getCompletionDate)).collect(Collectors.toList());
  }

  public void updateTask(Task task) {
    for (int i = 0; i < getTasks().size(); i++) {
      if (getTasks().get(i).getID() == task.getID()) {
        getTasks().set(i, task);
      }
    }
  }

  public void removeTask(int ID) {
    tasks.remove(findTaskByID(ID));
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
