// TODO: https://stackoverflow.com/questions/24491260/mocking-time-in-java-8s-java-time-api
// TODO: https://stackoverflow.com/questions/33942544/how-to-skip-weekends-while-adding-days-to-localdate-in-java-8
// TODO: Move holidays to a different, configurable file

package com.organiser.file.util;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;

/**
 * {@code TimeMachine} contains all time and date specific code for the application.  It allows for
 * switching the current date seen by the application to a arbitrary fixed date through the use of
 * {@link java.time.Clock}, as well as stores the configuration for holidays and provides methods to
 * check whether a date is a holiday and to calculate a new date a set number of business days
 * before another date.
 *
 * @see java.time.Clock
 * @see java.time.LocalDate
 */
public class TimeMachine {

  private static Clock clock = Clock.systemDefaultZone();
  private static final ZoneId zoneId = ZoneId.systemDefault();

  private TimeMachine() {}

  /**
   * Obtains the current date from the clock currently set to {@link #clock}.
   *
   * @return the current date for the set clock
   */
  public static LocalDate now() {
    return LocalDate.now(getClock());
  }

  /**
   * Sets the clock used by {@link TimeMachine} to be fixed at a specific date.
   *
   * @param date the date to be used
   */
  public static void useFixedClockAt(LocalDate date){
    clock = Clock.fixed(date.atStartOfDay(zoneId).toInstant(), zoneId);
  }

  /**
   * Sets the clock used by {@link TimeMachine} to be the system clock.
   */
  public static void useSystemDefaultZoneClock(){
    clock = Clock.systemDefaultZone();
  }

  /**
   * Gets the current set clock.
   *
   * @return current clock
   */
  private static Clock getClock() {
    return clock ;
  }

  /**
   * Calculates a new date a set number of business days before another date.
   *
   * @param daysBefore number of business days
   * @param completionDate base date from which an earlier date is calculated
   * @return a date that is {@code daysBefore} earlier than {@code completionDate}
   */
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

  /**
   * Retrieves the configured holiday dates.
   *
   * @return Array of holiday dates
   */
  private static LocalDate[] retrieveNationalHolidays() {
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

  /**
   * Checks whether a date is a holiday. Compares the date to Saturday, Sunday and the array of
   * holidays returned by {@link #retrieveNationalHolidays()}
   *
   * @param date date to be checked
   * @return true if the date is a holiday, false if not
   */
  public static boolean isHoliday(LocalDate date) {
    return date.getDayOfWeek() == DayOfWeek.SATURDAY
        || date.getDayOfWeek() == DayOfWeek.SUNDAY || Arrays.asList(retrieveNationalHolidays()).contains(date);
  }
}
