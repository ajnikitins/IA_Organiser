package com.organiser.file.exceptions;

/**
 * Signals that a certain date is a holiday.
 * Holidays are configured in {@code com.zalktis.file.util.TimeMachine}.
 *
 * @see com.organiser.file.util.TimeMachine
 * @see com.organiser.file.obj.Order
 * @see com.organiser.file.obj.Task
 */
public class HolidayException extends RuntimeException {

  public static final String DEFAULT_MESSAGE = "The date: %s is a holiday.";

  /** Constructs a new Holiday exception with {@code null} as its
   * detail message.  The cause is not initialized, and may subsequently be
   * initialized by a call to {@link #initCause}.
   */
  public HolidayException() {
    super();
  }

  /** Constructs a new Holiday exception with the specified detail message.
   * The cause is not initialized, and may subsequently be initialized by a
   * call to {@link #initCause}.
   *
   * @param   message   the detail message. The detail message is saved for
   *          later retrieval by the {@link #getMessage()} method.
   */
  public HolidayException(String message) {
    super(message);
  }
}
