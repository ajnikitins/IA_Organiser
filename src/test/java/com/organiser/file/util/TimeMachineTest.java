package com.organiser.file.util;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TimeMachineTest {

  @BeforeAll
  static void setUp() {
    TimeMachine.useFixedClockAt(LocalDate.of(2020, 11, 16));
  }

  @Test
  void getDate() {
    assertEquals(TimeMachine.now().minusDays(3), TimeMachine.getDate(1,TimeMachine.now()));
  }

  @Test
  void isNotHolidayWithRegularWorkday() {
    assertFalse(TimeMachine.isHoliday(TimeMachine.now()));
  }

  @Test
  void isHolidayWithWeekendDay() {
    assertTrue(TimeMachine.isHoliday(TimeMachine.now().minusDays(1)));
  }

  @Test
  void isHolidayWithNationalHoliday() {
    assertTrue(TimeMachine.isHoliday(TimeMachine.now().plusDays(2)));
  }

  @AfterAll
  static void tearDown() {
    TimeMachine.useSystemDefaultZoneClock();
  }
}