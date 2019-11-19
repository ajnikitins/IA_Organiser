package com.zalktis.file.obj;

import static org.junit.jupiter.api.Assertions.*;

import com.zalktis.file.exceptions.DateBeforeTodayException;
import java.time.LocalDate;
import java.time.Period;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskTest {

  private Task task;

  @BeforeEach
  void setUp() {
    task = new Task(0, 0, "Print", "None", LocalDate.of(2020, 12, 3), 4);
  }

  @Test
  void getID() {
    assertEquals(0, task.getID());
  }

  @Test
  void getParentID() {
    assertEquals(0, task.getParentID());
  }

  @Test
  void getAndSetName() {
    assertEquals("Print", task.getName());
    task.setName("Buy paper");
    assertEquals("Buy paper", task.getName());
  }

  @Test
  void getAndSetDetails() {
    assertEquals("None", task.getDetails());
    task.setDetails("Many");
    assertEquals("Many", task.getDetails());
  }

  @Test
  void setDaysBeforeOrderAndFail() {
    assertThrows(DateBeforeTodayException.class, () -> task.setDaysBeforeOrder(9999));
  }

  @Test
  void getAndSetDaysBeforeOrder() {
    assertEquals(4, task.getDaysBeforeOrder());
    task.setDaysBeforeOrder(6);
    assertEquals(6, task.getDaysBeforeOrder());
  }

  @Test
  void setOrderCompletionDateAndFail() {
    assertThrows(DateBeforeTodayException.class, () -> task.setOrderCompletionDate(LocalDate.of(1920, 1, 1)));
  }

  @Test
  void getAndSetOrderCompletionDate() {
    assertEquals(LocalDate.of(2020, 12, 3), task.getOrderCompletionDate());
    task.setOrderCompletionDate(LocalDate.of(2021, 9, 4));
    assertEquals(LocalDate.of(2021, 9, 4), task.getOrderCompletionDate());
  }

  @Test
  void getAndCompletionDate() {
    assertEquals(LocalDate.of(2020, 11, 27), task.getCompletionDate());
    task.setDaysBeforeOrder(5);
    assertEquals(LocalDate.of(2020, 11, 26), task.getCompletionDate());
  }

}