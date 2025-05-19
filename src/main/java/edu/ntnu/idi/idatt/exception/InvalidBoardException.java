package edu.ntnu.idi.idatt.exception;

/**
 * Throws exception if the JSON file that describes a board is invalid or unreadable.
 */
public class InvalidBoardException extends Exception {

  public InvalidBoardException(String message) {
    super(message);
  }

  public InvalidBoardException(String message, Throwable cause) {
    super(message, cause);
  }
}

