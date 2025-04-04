package edu.ntnu.idi.idatt.model;

import com.google.gson.Gson;
import edu.ntnu.idi.idatt.exception.InvalidBoardException;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoardGameFactory {
  private final ArrayList<String> paths;

  public BoardGameFactory() throws URISyntaxException {
    this.paths = new ArrayList<>();
    this.paths.add("/boards/board2.json");
  }

  private final JsonBoardFileHandler boardFileHandler = new JsonBoardFileHandler();

  /**
   * Reads JSON from file and builds a Board object.
   *
   * @param filePath Path to file containing board definition.
   *                 The file should be in JSON format.
   * @return Board object with fields from file.
   */
  public Board createBoardFromFile(String filePath) throws InvalidBoardException {
    return boardFileHandler.readBoardFromJsonFile(filePath);
  }

  /**
   * Creates board game from file.
   * Initiates board, dice and empty player list.
   *
   * @param boardId Integer determining file containing board definition.
   *                 The file should be in JSON format.
   * @return BoardGame object with board from file.
   */
  public BoardGame createBoardGameFromFile(int boardId) throws InvalidBoardException {
    Board board = createBoardFromFile(paths.get(boardId));

    return new BoardGame(board);
  }
}