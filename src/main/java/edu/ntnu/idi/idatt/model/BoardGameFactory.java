package edu.ntnu.idi.idatt.model;

import edu.ntnu.idi.idatt.exception.InvalidBoardException;
import java.nio.file.Path;

public class BoardGameFactory {

  private final JsonBoardFileHandler boardFileHandler = new JsonBoardFileHandler();

  /**
   * Creates a classic board with 90 fields.
   *
   * @return Board object with 90 fields.
   */
  public Board createClassicBoard() {
    // TODO: Hardcode et klassisk stigespillbrett med 90 felter kanskje?
    return null;
  }

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