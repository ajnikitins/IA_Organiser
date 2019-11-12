package com.zalktis.file.exceptions;

public class DateBeforeTodayException extends RuntimeException {
  public DateBeforeTodayException(String message) {
    super(message);
  }
}
