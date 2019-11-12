package com.zalktis.file.obj;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zalktis.file.Holidays;
import com.zalktis.file.util.LocalDateDeserializer;
import com.zalktis.file.util.LocalDateSerializer;
import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable {

  @JsonProperty("ID")
  private int ID;

  @JsonProperty("parent")
  @JsonBackReference
  private Order parent;

  @JsonProperty("name")
  private String name;

  @JsonProperty("details")
  private String details;

  @JsonProperty("daysBeforeOrder")
  private int daysBeforeOrder;

  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonSerialize(using = LocalDateSerializer.class)
  private LocalDate completionDate;

  public Task(int ID, Order parent, String name, String details, int daysBeforeOrder, LocalDate completionDate) {
    this.ID = ID;
    this.parent = parent;
    this.name = name;
    this.details = details;
    this.daysBeforeOrder = daysBeforeOrder;
    this.completionDate = completionDate;
  }

  public Task(int ID, Order parent, String name, String details, int daysBeforeOrder) {
    this.ID = ID;
    this.parent = parent;
    this.name = name;
    this.details = details;
    this.daysBeforeOrder = daysBeforeOrder;
    this.completionDate = Holidays.getDate(daysBeforeOrder, parent.getCompletionDate());
  }

  @JsonProperty("ID")
  public int getID() {
    return ID;
  }

  @JsonProperty("parent")
  public Order getParent() {
    return parent;
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
}
