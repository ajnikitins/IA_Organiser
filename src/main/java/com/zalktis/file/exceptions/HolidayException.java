package com.zalktis.file.exceptions;

public class HolidayException extends RuntimeException {

  public static final String DEFAULT_MESSAGE = "The date: %s is a holiday.";

  public HolidayException() {
    super();
  }

  public HolidayException(String message) {
    super(message);
  }
}
