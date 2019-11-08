package com.zalktis.file.obj;

import com.zalktis.file.Holidays;
import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable {
  private int ID;
  private Order parent;
  private String name;
  private String details;
  private int daysBeforeOrder;
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

  public int getID() {
    return ID;
  }

  public Order getParent() {
    return parent;
  }

  public String getName() {
    return name;
  }

  public String getDetails() {
    return details;
  }

  public int getDaysBeforeOrder() {
    return daysBeforeOrder;
  }

  public LocalDate getCompletionDate() {
    return completionDate;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public void setDaysBeforeOrder(int daysBeforeOrder) {
    this.daysBeforeOrder = daysBeforeOrder;
  }
}
