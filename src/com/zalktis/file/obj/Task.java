package com.zalktis.file.obj;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zalktis.file.Holidays;
import com.zalktis.file.exceptions.DateBeforeTodayException;
import com.zalktis.file.util.LocalDateDeserializer;
import com.zalktis.file.util.LocalDateSerializer;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Task implements Serializable {

  @JsonProperty("ID")
  private int ID;

  @JsonProperty("parentID")
  private int parentID;

  @JsonProperty("name")
  private String name;

  @JsonProperty("details")
  private String details;

  @JsonProperty("orderCompletionDate")
  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonSerialize(using = LocalDateSerializer.class)
  private LocalDate orderCompletionDate;

  @JsonProperty("daysBeforeOrder")
  private int daysBeforeOrder;

  @JsonProperty("completionDate")
  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonSerialize(using = LocalDateSerializer.class)
  private LocalDate completionDate;

  public Task() {}

  public Task(int ID, int parentID, String name, String details, LocalDate orderCompletionDate, int daysBeforeOrder) {
    this.ID = ID;
    this.parentID = parentID;
    setName(name);
    setDetails(details);
    setOrderCompletionDate(orderCompletionDate);
    setDaysBeforeOrder(daysBeforeOrder);
  }

  @JsonProperty("ID")
  public int getID() {
    return ID;
  }

  @JsonProperty("parentID")
  public int getParentID() {
    return parentID;
  }

  @JsonProperty("name")
  public String getName() {
    return name;
  }

  @JsonProperty("details")
  public String getDetails() {
    return details;
  }

  @JsonProperty("daysBeforeOrder")
  public int getDaysBeforeOrder() {
    return daysBeforeOrder;
  }

  @JsonProperty("orderCompletionDate")
  public LocalDate getOrderCompletionDate() {
    return orderCompletionDate;
  }

  @JsonProperty("completionDate")
  public LocalDate getCompletionDate() {
    return completionDate;
  }

  @JsonProperty("name")
  public void setName(String name) {
    this.name = name;
  }

  @JsonProperty("details")
  public void setDetails(String details) {
    this.details = details;
  }

  @JsonProperty("orderCompletionDate")
  public void setOrderCompletionDate(LocalDate orderCompletionDate) {
    calculateCompletionDate(orderCompletionDate, daysBeforeOrder);
    this.orderCompletionDate = orderCompletionDate;
  }

  @JsonProperty("daysBeforeOrder")
  public void setDaysBeforeOrder(int daysBeforeOrder) {
    calculateCompletionDate(orderCompletionDate, daysBeforeOrder);
    this.daysBeforeOrder = daysBeforeOrder;
  }

  private void calculateCompletionDate(LocalDate orderCompletionDate, int daysBeforeOrder) {
    LocalDate calculatedCompletionDate = Holidays.getDate(daysBeforeOrder, orderCompletionDate);

    if (calculatedCompletionDate.isBefore(LocalDate.now())) {
      throw new DateBeforeTodayException("The calculated task completion date: " + calculatedCompletionDate + " is before today: " + LocalDate.now());
    } else {
      this.completionDate = calculatedCompletionDate;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Task task = (Task) o;
    return ID == task.ID &&
        parentID == task.parentID &&
        daysBeforeOrder == task.daysBeforeOrder &&
        name.equals(task.name) &&
        details.equals(task.details) &&
        completionDate.equals(task.completionDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ID, parentID, name, details, daysBeforeOrder, completionDate);
  }
}
