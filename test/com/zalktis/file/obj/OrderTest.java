package com.zalktis.file.obj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.zalktis.file.exceptions.DateBeforeTodayException;
import com.zalktis.file.exceptions.HolidayException;
import com.zalktis.file.util.TimeMachine;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
class OrderTest {

  private static final LocalDate LOCAL_DATE = LocalDate.of(2020, 12, 14);

  private Order order;

  @BeforeEach
  void setUp() {
    TimeMachine.useFixedClockAt(LOCAL_DATE);
    order = new Order(0, "Ad page", "Latv. val", "none", TimeMachine.now().plusDays(7));
  }

  @Test
  @org.junit.jupiter.api.Order(1)
  void setAndGetID() {
    order.setID(1);
    assertEquals(1, order.getID());
  }

  @Test
  @org.junit.jupiter.api.Order(2)
  void setAndGetName() {
    order.setName("Notebook");
    assertEquals("Notebook", order.getName());
  }

  @Test
  @org.junit.jupiter.api.Order(3)
  void setAndGetCustomerName() {
    order.setCustomerName("VELVE");
    assertEquals("VELVE", order.getCustomerName());
  }

  @Test
  @org.junit.jupiter.api.Order(4)
  void setAndGetDetails() {
    order.setDetails("Many");
    assertEquals("Many", order.getDetails());
  }

  @Test
  @org.junit.jupiter.api.Order(5)
  void setAndGetCompletionDate() {
    order.setCompletionDate(TimeMachine.now().plusDays(4));
    assertEquals(TimeMachine.now().plusDays(4), order.getCompletionDate());
  }

  @Test
  @org.junit.jupiter.api.Order(6)
  void setCompletionDateAndFailWithDateBefore() {
    assertThrows(DateBeforeTodayException.class, () -> order.setCompletionDate(TimeMachine.now().minusDays(5)));
  }

  @Test
  @org.junit.jupiter.api.Order(7)
  void setCompletionDateAndFailWithHoliday() {
    assertThrows(HolidayException.class, () -> order.setCompletionDate(TimeMachine.now().plusDays(5)));
  }

  @Test
  @org.junit.jupiter.api.Order(8)
  void getTasks() {
    assertEquals(new ArrayList<Task>(), order.getTasks());
  }

  @Test
  @org.junit.jupiter.api.Order(9)
  void addAndFindAndRemoveTask() {
    order.addTask("Print", "None", 4);

    Task task = new Task(0, 0, "Print", "None", order.getCompletionDate(), 4);
    assertEquals(task, order.findTaskByID(0));

    order.removeTask(0);
    assertNull(order.findTaskByID(0));
  }

  @AfterEach
  void tearDown() {
    TimeMachine.useSystemDefaultZoneClock();
  }
}