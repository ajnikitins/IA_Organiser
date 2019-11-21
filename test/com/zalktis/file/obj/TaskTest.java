package com.zalktis.file.obj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.zalktis.file.exceptions.DateBeforeTodayException;
import com.zalktis.file.exceptions.HolidayException;
import com.zalktis.file.util.TimeMachine;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class TaskTest {

  private static final LocalDate LOCAL_DATE = LocalDate.of(2020, 12, 14);

  private Task task;

  @BeforeAll
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
  void getAndCompletionDate() {
    assertEquals(TimeMachine.now().plusDays(7), task.getCompletionDate());
    task.setDaysBeforeOrder(4);
    assertEquals(TimeMachine.now().plusDays(8), task.getCompletionDate());
  }

  @AfterAll
  void tearDown() {
    TimeMachine.useSystemDefaultZoneClock();
  }
}