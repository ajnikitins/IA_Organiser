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
import java.util.Objects;

public class Task {

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

  public Task(String name, String details, int daysBeforeOrder) {
    setName(name);
    setDetails(details);
    setDaysBeforeOrder(daysBeforeOrder);
  }

  @Deprecated
  public Task(int ID, int parentID, String name, String details, LocalDate orderCompletionDate, int daysBeforeOrder) {
    this(name, details, daysBeforeOrder);
    setID(ID);
    setParentID(parentID);
    setOrderCompletionDate(orderCompletionDate);
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

  @JsonProperty("ID")
  public void setID(int ID) {
    this.ID = ID;
  }

  @JsonProperty("parentID")
  public void setParentID(int parentID) {
    this.parentID = parentID;
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
    if (daysBeforeOrder > -1) {
      if (TimeMachine.isHoliday(orderCompletionDate)) {
        throw new HolidayException(
            String.format(HolidayException.DEFAULT_MESSAGE, orderCompletionDate));
      }
      calculateCompletionDate(orderCompletionDate, daysBeforeOrder);
      this.orderCompletionDate = orderCompletionDate;
    }
  }

  @JsonProperty("daysBeforeOrder")
  public void setDaysBeforeOrder(int daysBeforeOrder) {
    if (orderCompletionDate != null) {
      calculateCompletionDate(orderCompletionDate, daysBeforeOrder);
    }
    this.daysBeforeOrder = daysBeforeOrder;
  }

  private void calculateCompletionDate(LocalDate orderCompletionDate, int daysBeforeOrder) {
    LocalDate calculatedCompletionDate = TimeMachine.getDate(daysBeforeOrder, orderCompletionDate);

    if (calculatedCompletionDate.isBefore(TimeMachine.now())) {
      throw new DateBeforeTodayException(String.format(DateBeforeTodayException.DEFAULT_MESSAGE, calculatedCompletionDate));
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
