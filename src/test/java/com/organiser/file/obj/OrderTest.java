package com.organiser.file.obj;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.organiser.file.exceptions.DateBeforeTodayException;
import com.organiser.file.exceptions.HolidayException;
import com.organiser.file.util.TimeMachine;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderTest {

  private static final LocalDate LOCAL_DATE = LocalDate.of(2020, 12, 14);

  private Order order;

  @BeforeEach
  void setUp() {
    TimeMachine.useFixedClockAt(LOCAL_DATE);
    order = new Order("Ad page", "Latv. val", "none", TimeMachine.now().plusDays(7));
  }

  @Test
  void setAndGetCompletionDate() {
    order.setCompletionDate(TimeMachine.now().plusDays(4));
    assertEquals(TimeMachine.now().plusDays(4), order.getCompletionDate());
  }

  @Test
  void setCompletionDateAndFailWithDateBefore() {
    assertThrows(DateBeforeTodayException.class, () -> order.setCompletionDate(TimeMachine.now().minusDays(5)));
  }

  @Test
  void setCompletionDateAndFailWithHoliday() {
    assertThrows(HolidayException.class, () -> order.setCompletionDate(TimeMachine.now().plusDays(5)));
  }

  @Test
  void addAndFindAndRemoveTask() {
    order.addTask(new Task("Print", "None", 4));

    assertEquals("Print", order.findTaskByID(0).getName());

    order.removeTask(0);
    assertNull(order.findTaskByID(0));
  }

  @Test
  void getSortedTasks() {
    order.addTask(new Task("Order paper", "", 4));
    order.addTask(new Task("Order forms", "", 5));

    assertTrue(order.getSortedTasks().get(0).getCompletionDate().isBefore(order.getSortedTasks().get(1).getCompletionDate()));
  }

  @Test
  void failValidateProperties() {
    boolean[] expected = new boolean[] {true, true, true, true};
    assertArrayEquals(expected, Order.validateProperties("", "", TimeMachine.now().minusDays(1)));
  }

  @Test
  void succeedValidateProperties() {
    boolean[] expected = new boolean[] {false, false, false, false};
    assertArrayEquals(expected, Order.validateProperties("Print", "VELVE", TimeMachine.now()));
  }

  @AfterEach
  void tearDown() {
    TimeMachine.useSystemDefaultZoneClock();
  }
}