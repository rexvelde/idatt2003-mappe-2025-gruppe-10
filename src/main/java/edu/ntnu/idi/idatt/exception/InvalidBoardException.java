package edu.ntnu.idi.idatt.exception;

/**
 * Throws exception if the JSON file that describes a board is invalid or unreadable.
 */
public class InvalidBoardException extends Exception {

  /**
   * Constructs a new InvalidBoardException with the specified detail message.
   *
   * @param message the detail message
   */
  public InvalidBoardException(String message) {
    super(message);
  }

    /**
     * Constructs a new InvalidBoardException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     */
  public InvalidBoardException(String message, Throwable cause) {
    super(message, cause);
  }
}

