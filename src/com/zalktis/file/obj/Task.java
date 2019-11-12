package com.zalktis.file.obj;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zalktis.file.Holidays;
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

  @JsonProperty("daysBeforeOrder")
  private int daysBeforeOrder;

  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonSerialize(using = LocalDateSerializer.class)
  private LocalDate completionDate;

  public Task() {}

  public Task(int ID, int parentID, String name, String details, int daysBeforeOrder, LocalDate orderCompletionDate) {
    this.ID = ID;
    this.parentID = parentID;
    this.name = name;
    this.details = details;
    this.daysBeforeOrder = daysBeforeOrder;
    this.completionDate = Holidays.getDate(daysBeforeOrder, orderCompletionDate);
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

  @JsonProperty("daysBeforeOrder")
  public void setDaysBeforeOrder(int daysBeforeOrder) {
    this.daysBeforeOrder = daysBeforeOrder;
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
