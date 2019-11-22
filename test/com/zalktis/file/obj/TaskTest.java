package com.zalktis.file.obj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.zalktis.file.exceptions.DateBeforeTodayException;
import com.zalktis.file.exceptions.HolidayException;
import com.zalktis.file.util.TimeMachine;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
class TaskTest {

  private static final LocalDate LOCAL_DATE = LocalDate.of(2020, 12, 14);

  private Task task;

  @BeforeEach
  void setUp() {
    TimeMachine.useFixedClockAt(LOCAL_DATE);
    task = new Task(0, 0, "Print", "None", TimeMachine.now().plusDays(7), 4);
  }

  @Test
  @Order(1)
  void getID() {
    assertEquals(0, task.getID());
  }

  @Test
  @Order(2)
  void getParentID() {
    assertEquals(0, task.getParentID());
  }

  @Test
  @Order(3)
  void getAndSetName() {
    assertEquals("Print", task.getName());
    task.setName("Buy paper");
    assertEquals("Buy paper", task.getName());
  }

  @Test
  @Order(4)
  void getAndSetDetails() {
    assertEquals("None", task.getDetails());
    task.setDetails("Many");
    assertEquals("Many", task.getDetails());
  }

  @Test
  @Order(5)
  void setDaysBeforeOrderAndFailWithDateBefore() {
    assertThrows(DateBeforeTodayException.class, () -> task.setDaysBeforeOrder(9999));
  }

  @Test
  @Order(6)
  void getAndSetDaysBeforeOrder() {
    assertEquals(4, task.getDaysBeforeOrder());
    task.setDaysBeforeOrder(5);
    assertEquals(5, task.getDaysBeforeOrder());
  }

  @Test
  @Order(7)
  void setOrderCompletionDateAndFailWithDateBefore() {
    assertThrows(DateBeforeTodayException.class, () -> task.setOrderCompletionDate(TimeMachine.now().minusDays(3)));
  }

  @Test
  @Order(8)
  void setOrderCompletionDateAndFailWithHoliday() {
    assertThrows(HolidayException.class, () -> task.setOrderCompletionDate(TimeMachine.now().minusDays(1)));
  }

  @Test
  @Order(9)
  void getAndSetOrderCompletionDate() {
    assertEquals(TimeMachine.now().plusDays(7), task.getOrderCompletionDate());
    task.setOrderCompletionDate(TimeMachine.now().plusDays(14));
    assertEquals(TimeMachine.now().plusDays(14), task.getOrderCompletionDate());
  }

  @Test
  @Order(10)
  void getAndRecalculateCompletionDate() {
    assertEquals(TimeMachine.now().plusDays(1), task.getCompletionDate());
    task.setDaysBeforeOrder(5);
    assertEquals(TimeMachine.now(), task.getCompletionDate());
  }

  @AfterEach
  void tearDown() {
    TimeMachine.useSystemDefaultZoneClock();
  }
}