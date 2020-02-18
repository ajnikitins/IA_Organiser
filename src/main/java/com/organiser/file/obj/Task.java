package com.organiser.file.obj;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.organiser.file.exceptions.DateBeforeTodayException;
import com.organiser.file.exceptions.HolidayException;
import com.organiser.file.util.LocalDateDeserializer;
import com.organiser.file.util.LocalDateSerializer;
import com.organiser.file.util.TimeMachine;
import java.time.LocalDate;

/**
 * Container for all information specific to a single task.
 */
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

  /**
   * Construct an empty Task for use by {@link com.fasterxml.jackson.databind.ObjectMapper} and
   * {@link #Task(String, String, int)}
   */
  public Task() {}

  /**
   *  Constructs a new Order with a known name, task details and days before the order completion
   *  date.
   *
   * @param name name of order
   * @param details order details
   * @param daysBeforeOrder date when order must be completed
   */

  // TODO: Rewrite task adding and constructor to not use daysBeforeOrder in the constructor.
  public Task(String name, String details, int daysBeforeOrder) {
    setName(name);
    setDetails(details);
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

  /**
   * Sets a new completion date from the parent order and recalculates the completion date for the
   * task.
   *
   * @param orderCompletionDate new order completion date
   * @throws DateBeforeTodayException if the new date has already passed (before today)
   * @throws HolidayException if the new date is a holiday
   *
   * @see Task#setOrderCompletionDate(LocalDate)
   */
  @JsonProperty("orderCompletionDate")
  public void setOrderCompletionDate(LocalDate orderCompletionDate) {
    if (TimeMachine.isHoliday(orderCompletionDate)) {
      throw new HolidayException(
          String.format(HolidayException.DEFAULT_MESSAGE, orderCompletionDate));
    }
    calculateCompletionDate(orderCompletionDate, daysBeforeOrder);
    this.orderCompletionDate = orderCompletionDate;
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

  /**
   * Validates probable properties for new Orders.
   * Values are validated in such order:
   * Length of the task name;
   * Is completion date before today;
   * @param name name of order to be validated
   * @param daysBeforeOrder number of days before the order completion date, when the task has to be completed
   * @param orderCompletionDate completion date for order to be validated
   * @return List of validation error messages.
   */
  public static boolean[] validateProperties(String name, int daysBeforeOrder, LocalDate orderCompletionDate) {
    return new boolean[] {
        name.length() == 0,
        TimeMachine.getDate(daysBeforeOrder, orderCompletionDate).isBefore(TimeMachine.now())
    };
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
}
