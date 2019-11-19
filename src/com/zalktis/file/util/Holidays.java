// TODO: https://stackoverflow.com/questions/33942544/how-to-skip-weekends-while-adding-days-to-localdate-in-java-8/33943576
// TODO: Include National Holidays

package com.zalktis.file.util;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class Holidays {
  public static LocalDate getDate(int daysBefore, LocalDate completionDate) {
    if (daysBefore < 1) {
      return completionDate;
    }

    LocalDate result = completionDate;
    int removeDays = 0;
    while (removeDays < daysBefore) {
      result = result.minusDays(1);
      if (!(result.getDayOfWeek() == DayOfWeek.SATURDAY ||
          result.getDayOfWeek() == DayOfWeek.SUNDAY)) {
        ++removeDays;
      }
    }

    return result;
  }
}
