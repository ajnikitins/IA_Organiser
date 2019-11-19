package com.zalktis.file.obj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.zalktis.file.exceptions.DateBeforeTodayException;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderTest {

  private Order order;

  @BeforeEach
  void setUp() {
    order = new Order();
  }

  @Test
  void setAndGetID() {
    order.setID(0);
    assertEquals(0, order.getID());
  }

  @Test
  void setAndGetName() {
    order.setName("Ad page");
    assertEquals("Ad page", order.getName());
  }

  @Test
  void setAndGetCustomerName() {
    order.setCustomerName("VELVE");
    assertEquals("VELVE", order.getCustomerName());
  }

  @Test
  void setAndGetDetails() {
    order.setDetails("Many");
    assertEquals("Many", order.getDetails());
  }

  @Test
  void setAndGetCompletionDate() {
    order.setCompletionDate(LocalDate.of(2020,12,3));
    assertEquals(LocalDate.of(2020, 12, 3), order.getCompletionDate());
  }

  @Test
  void setCompletionDateAndFail() {
    assertThrows(DateBeforeTodayException.class, () -> order.setCompletionDate(LocalDate.of(1995,12,3)));
  }

  @Test
  void getTasks() {
    assertEquals(new ArrayList<Task>(), order.getTasks());
  }

  @Test
  void addAndFindAndRemoveTask() {
    order.setCompletionDate(LocalDate.of(2020, 12, 3));
    order.addTask("Print", "None", 4);

    Task task = new Task(0, 0, "Print", "None", LocalDate.of(2020, 12, 3), 4);
    assertEquals(task, order.findTaskByID(0));

    order.removeTask(0);
    assertNull(order.findTaskByID(0));
  }
}