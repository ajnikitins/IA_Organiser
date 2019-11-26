// TODO: https://stackoverflow.com/questions/24491260/mocking-time-in-java-8s-java-time-api

package com.zalktis.file.util;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;

public class TimeMachine {

  private static Clock clock = Clock.systemDefaultZone();
  private static ZoneId zoneId = ZoneId.systemDefault();

  private static LocalDate[] getNationalHolidays() {
    return new LocalDate[]{
        LocalDate.of(TimeMachine.now().getYear(), 1, 1),
        LocalDate.of(2020, 4, 10),
        LocalDate.of(2020, 4, 12),
        LocalDate.of(2020, 4, 13),
        LocalDate.of(TimeMachine.now().getYear(), 5, 1),
        LocalDate.of(TimeMachine.now().getYear(), 5, 4),
        LocalDate.of(TimeMachine.now().getYear(), 6, 23),
        LocalDate.of(TimeMachine.now().getYear(), 6, 24),
        LocalDate.of(TimeMachine.now().getYear(), 11, 18),
        LocalDate.of(TimeMachine.now().getYear(), 12, 24),
        LocalDate.of(TimeMachine.now().getYear(), 12, 25),
        LocalDate.of(TimeMachine.now().getYear(), 12, 26),
        LocalDate.of(TimeMachine.now().getYear(), 12, 31),
    };
  }

  public static LocalDate now() {
    return LocalDate.now(getClock());
  }

  public static void useFixedClockAt(LocalDate date){
    clock = Clock.fixed(date.atStartOfDay(zoneId).toInstant(), zoneId);
  }

  public static void useSystemDefaultZoneClock(){
    clock = Clock.systemDefaultZone();
  }

  private static Clock getClock() {
    return clock ;
  }

  public static LocalDate getDate(int daysBefore, LocalDate completionDate) {
    if (daysBefore < 1) {
      return completionDate;
    }

    LocalDate result = completionDate;
    int removeDays = 0;
    while (removeDays < daysBefore) {
      result = result.minusDays(1);
      if (!isHoliday(result)) {
        ++removeDays;
      }
    }

    return result;
  }

  public static boolean isHoliday(LocalDate date) {
    return date.getDayOfWeek() == DayOfWeek.SATURDAY
        || date.getDayOfWeek() == DayOfWeek.SUNDAY || Arrays.asList(getNationalHolidays()).contains(date);
  }
}
