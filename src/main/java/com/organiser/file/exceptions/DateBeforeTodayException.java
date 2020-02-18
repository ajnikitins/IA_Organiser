package com.organiser.file.exceptions;

import com.organiser.file.util.TimeMachine;

/**
 * Signals that a certain date is before today's date.
 *
 * @see com.organiser.file.obj.Task
 * @see com.organiser.file.obj.Order
 */
public class DateBeforeTodayException extends RuntimeException {

  public static final String DEFAULT_MESSAGE = "The calculated order completion date: %s is before today: " + TimeMachine.now();

  /** Constructs a new DateBeforeToday exception with {@code null} as its
   * detail message.  The cause is not initialized, and may subsequently be
   * initialized by a call to {@link #initCause}.
   */
  public DateBeforeTodayException() {
    super();
  }

  /** Constructs a new DateBeforeToday exception with the specified detail message.
   * The cause is not initialized, and may subsequently be initialized by a
   * call to {@link #initCause}.
   *
   * @param   message   the detail message. The detail message is saved for
   *          later retrieval by the {@link #getMessage()} method.
   */
  public DateBeforeTodayException(String message) {
    super(message);
  }
}
