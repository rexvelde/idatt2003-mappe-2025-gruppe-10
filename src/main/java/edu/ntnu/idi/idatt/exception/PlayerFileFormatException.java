package edu.ntnu.idi.idatt.exception;

/**
 * Throws exception if the CSV file with players has an invalid format or is missing information.
 */
public class PlayerFileFormatException extends Exception {

  /**
   * Constructs a new PlayerFileFormatException with the specified detail message.
   *
   * @param message the detail message
   */
  public PlayerFileFormatException(String message) {
    super(message);
  }

    /**
     * Constructs a new PlayerFileFormatException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     */
  public PlayerFileFormatException(String message, Throwable cause) {
    super(message, cause);
  }
}
