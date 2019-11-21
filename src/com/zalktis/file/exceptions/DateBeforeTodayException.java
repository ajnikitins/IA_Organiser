package com.zalktis.file.exceptions;

import com.zalktis.file.util.TimeMachine;

public class DateBeforeTodayException extends RuntimeException {

  public static final String DEFAULT_MESSAGE = "The calculated order completion date: %s is before today: " + TimeMachine.now();

  public DateBeforeTodayException() {
    super();
  }

  public DateBeforeTodayException(String message) {
    super(message);
  }
}
