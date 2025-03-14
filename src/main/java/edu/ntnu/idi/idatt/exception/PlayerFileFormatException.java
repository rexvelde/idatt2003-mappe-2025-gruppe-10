package edu.ntnu.idi.idatt.exception;

/**
 * Throws exception if the CSV file with players has an invalid format or is missing information.
 */
public class PlayerFileFormatException extends Exception {

  public PlayerFileFormatException(String message) {
    super(message);
  }

  public PlayerFileFormatException(String message, Throwable cause) {
    super(message, cause);
  }
}
