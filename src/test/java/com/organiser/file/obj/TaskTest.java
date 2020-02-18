package com.organiser.file.obj;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.organiser.file.exceptions.DateBeforeTodayException;
import com.organiser.file.exceptions.HolidayException;
import com.organiser.file.util.TimeMachine;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskTest {

  private static final LocalDate LOCAL_DATE = LocalDate.of(2020, 12, 14);

  private Task task;

  @BeforeEach
  void setUp() {
    TimeMachine.useFixedClockAt(LOCAL_DATE);
    task = new Task("Print", "None", 4);
    task.setOrderCompletionDate(TimeMachine.now().plusDays(4));
  }

  @Test
  void setDaysBeforeOrderAndFailWithDateBefore() {
    assertThrows(DateBeforeTodayException.class, () -> task.setDaysBeforeOrder(9999));
  }

  @Test
  void setDaysBeforeOrderAndNoFail() {
    task.setDaysBeforeOrder(3);
    assertEquals(3, task.getDaysBeforeOrder());
  }

  @Test
  void setOrderCompletionDateAndFailWithDateBefore() {
    assertThrows(DateBeforeTodayException.class, () -> task.setOrderCompletionDate(TimeMachine.now().minusDays(3)));
  }

  @Test
  void setOrderCompletionDateAndFailWithHoliday() {
    assertThrows(HolidayException.class, () -> task.setOrderCompletionDate(TimeMachine.now().minusDays(1)));
  }

  @Test
  void setOrderCompletionDateAndNoFail() {
    task.setOrderCompletionDate(TimeMachine.now().plusDays(14));
    assertEquals(TimeMachine.now().plusDays(14), task.getOrderCompletionDate());
  }

  @Test
  void recalculateCompletionDate() {
    task.setDaysBeforeOrder(4);
    assertEquals(TimeMachine.now(), task.getCompletionDate());
  }

  @Test
  void failValidateProperties() {
    boolean[] expected = new boolean[] {true, true};
    assertArrayEquals(expected, Task.validateProperties("", 1, TimeMachine.now()));
  }

  @Test
  void succeedValidateProperties() {
    boolean[] expected = new boolean[] {false, false};
    assertArrayEquals(expected, Task.validateProperties("Order paper", 1, TimeMachine.now().plusDays(1)));
  }

  @AfterEach
  void tearDown() {
    TimeMachine.useSystemDefaultZoneClock();
  }
}