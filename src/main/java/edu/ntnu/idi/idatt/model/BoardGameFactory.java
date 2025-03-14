package edu.ntnu.idi.idatt.model;

import edu.ntnu.idi.idatt.exception.InvalidBoardException;
import java.nio.file.Path;

public class BoardGameFactory {

  private final JsonBoardFileHandler boardFileHandler = new JsonBoardFileHandler();

  /**
   * Reads JSON from file and builds a Board object.
   *
   * @param filePath Path to file containing board definition.
   *                 The file should be in JSON format.
   * @return Board object with fields from file.
   */
  public Board createBoardFromFile(Path filePath) throws InvalidBoardException {
    return boardFileHandler.readBoardFromJsonFile(filePath);
  }

  /**
   * Creates board game from file.
   * Initiates board, dice and empty player list.
   *
   * @param filePath Path to file containing board definition.
   *                 The file should be in JSON format.
   * @return BoardGame object with board from file.
   */
  public BoardGame createBoardGameFromFile(Path filePath) throws InvalidBoardException {
    Board board = createBoardFromFile(filePath);
    BoardGame boardGame = new BoardGame(board);

    return boardGame;
  }
}