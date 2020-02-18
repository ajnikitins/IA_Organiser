package com.organiser.file.obj;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.organiser.file.exceptions.DateBeforeTodayException;
import com.organiser.file.exceptions.HolidayException;
import com.organiser.file.util.LocalDateDeserializer;
import com.organiser.file.util.LocalDateSerializer;
import com.organiser.file.util.TimeMachine;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Container for all information specific to a single order.
 * Also has methods to manipulate with the tasks for this order.
 */
public class Order {

  @JsonProperty("ID")
  private int ID;

  @JsonProperty("tasks")
  private final Map<Integer, Task> tasks;

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

  @JsonProperty("currentID")
  private int currentID = 0;

  @JsonIgnore
  private Runnable onChange;

  /**
   * Construct an empty Order for use by {@link com.fasterxml.jackson.databind.ObjectMapper} and
   * {@link #Order(String, String, String, LocalDate)}
   */
  public Order() {
    this.tasks = new TreeMap<>();
    this.onChange = () -> {};
  }

  /**
   *  Constructs a new Order with a known name, customer name, order details and a completion date.
   *
   * @param name name of order
   * @param customerName name of costumer
   * @param details order details
   * @param completionDate date when order must be completed
   */
  public Order(String name, String customerName, String details, LocalDate completionDate) {
    this();
    setName(name);
    setCustomerName(customerName);
    setDetails(details);
    setCompletionDate(completionDate);
  }

  @JsonProperty("ID")
  public int getID() {
    return ID;
  }

  @JsonProperty("tasks")
  public Map<Integer, Task> getTasks() {
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

  @JsonProperty("currentID")
  public int getCurrentID() {
    return currentID;
  }

  @JsonIgnore
  public Runnable getOnChange() {
    return onChange;
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

  @JsonProperty("currentID")
  public void setCurrentID(int currentID) {
    this.currentID = currentID;
  }

  @JsonIgnore
  public void setOnChange(Runnable onChange) {
    this.onChange = onChange;
  }

  /**
   * Sets a new completion date.  Checks are done to determine if the new date has already passed
   * (before today) and whether the new date is a holiday.  Also sets the new order completion date
   * for all {@link Task} instances belonging to the order.
   *
   * @param completionDate new order completion date
   * @throws DateBeforeTodayException if the new date has already passed (before today)
   * @throws HolidayException if the new date is a holiday
   *
   * @see Task#setOrderCompletionDate(LocalDate)
   */
  @JsonProperty("completionDate")
  public void setCompletionDate(LocalDate completionDate) {
    if (completionDate.isBefore(TimeMachine.now())) {
      throw new DateBeforeTodayException(String.format(DateBeforeTodayException.DEFAULT_MESSAGE, completionDate));
    } else if (TimeMachine.isHoliday(completionDate)) {
      throw new HolidayException(String.format(HolidayException.DEFAULT_MESSAGE, completionDate));
    } else {
      this.completionDate = completionDate;
      for (Task task: tasks.values()) {
        task.setOrderCompletionDate(completionDate);
      }
    }
  }

  /**
   * Adds a new {@link Task} instance to the task list.  Sets the incremented tasks ID,
   * the parent/order ID and the order completion date.
   *
   * @param task task to be added
   */
  public void addTask(Task task) {
    task.setID(currentID);
    task.setParentID(ID);
    task.setOrderCompletionDate(completionDate);
    tasks.put(currentID, task);
    currentID++;

    onChange.run();
  }

  /**
   * Searches the task list for a specific task by its ID.
   *
   * @param ID ID of the sought task
   * @return a {@link Task} instance if one is found, null if there is no task with the given ID
   */
  public Task findTaskByID(int ID) {
    return tasks.get(ID);
  }

  /**
   * Obtains the task list sorted ascending by the task completion date.  Uses the
   * {@link java.util.stream.Stream} and {@link Comparator} APIs.
   *
   * @return sorted task list
   */
  public List<Task> getSortedTasks() {
    return tasks.values().stream().sorted(Comparator.comparing(Task::getCompletionDate)).collect(Collectors.toList());
  }

  /**
   * Searches for a task by its ID, and removes it.
   *
   * @param ID ID of task to be removed
   */
  public void removeTask(int ID) {
    tasks.remove(ID);
    onChange.run();
  }

  /**
   * Validates probable properties for new Orders.
   * Values are validated in such order:
   * Length of the order name;
   * Length of the customer name;
   * Is completion date before today;
   * Is completion date a holiday.
   * @param name name of order to be validated
   * @param customerName customer name for order to be validated
   * @param completionDate completion date for order to be validated
   * @return List of validation error messages.
   */
  public static boolean[] validateProperties(String name, String customerName, LocalDate completionDate) {
    return new boolean[]{
        name.length() == 0,
        customerName.length() == 0,
        completionDate.isBefore(TimeMachine.now()),
        TimeMachine.isHoliday(completionDate)
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
    Order order = (Order) o;
    return ID == order.ID &&
        tasks.equals(order.tasks) &&
        name.equals(order.name) &&
        customerName.equals(order.customerName) &&
        details.equals(order.details) &&
        completionDate.equals(order.completionDate);
  }
}
