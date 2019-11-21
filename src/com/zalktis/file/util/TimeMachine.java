// TODO: https://stackoverflow.com/questions/24491260/mocking-time-in-java-8s-java-time-api

package com.zalktis.file.util;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;

public class TimeMachine {

  private static Clock clock = Clock.systemDefaultZone();
  private static ZoneId zoneId = ZoneId.systemDefault();

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
        || date.getDayOfWeek() == DayOfWeek.SUNDAY;
  }
}
