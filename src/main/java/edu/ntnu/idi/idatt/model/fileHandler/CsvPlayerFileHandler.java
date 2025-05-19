package edu.ntnu.idi.idatt.model.fileHandler;

import edu.ntnu.idi.idatt.exception.InvalidBoardException;
import edu.ntnu.idi.idatt.exception.PlayerFileFormatException;
import edu.ntnu.idi.idatt.model.player.Player;
import edu.ntnu.idi.idatt.model.board.Board;
import edu.ntnu.idi.idatt.model.board.BoardGame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvPlayerFileHandler {

  /**
   * Reads a CSV file with player information and returns a list of players.
   *
   * @param csvFile Path to the CSV file.
   * @return List of players.
   * @throws PlayerFileFormatException if an error occurs while reading the file.
   */
  public List<Player> readPlayers(Path csvFile) throws PlayerFileFormatException {
    List<Player> players = new ArrayList<>();

    try (BufferedReader br = Files.newBufferedReader(csvFile)) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts.length != 2) {
          throw new PlayerFileFormatException("Invalid line: " + line);
        }

        String name = parts[0].trim();
        String piece = parts[1].trim();

        Player player = new Player(name, piece);
        players.add(player);
      }

    } catch (IOException e) {
      throw new PlayerFileFormatException("Error reading CSV file: " + csvFile, e);
    }
    return players;
  }

  /**
   * Writes a list of players to a CSV file.
   *
   * @param players List of players to write.
   * @param csvFile Path to the CSV file.
   * @throws PlayerFileFormatException if an error occurs while writing the file.
   */
  public void writePlayersToCsv(List<Player> players, Path csvFile)
      throws PlayerFileFormatException {
    try (BufferedWriter bw = Files.newBufferedWriter(csvFile)) {
      for (Player player : players) {
        String line = player.getName() + "," + player.getPiece();
        bw.write(line);
        bw.newLine();
      }
    } catch (IOException e) {
        throw new PlayerFileFormatException("Error writing CSV file: " + csvFile, e);
    }
  }

  public static interface BoardGameObserver {
      void onTurnChanged(Player player);
      void onPlayerMoved(Player player, int fromTileId, int toTileId);
      void onGameEnded(Player winner);
  }

  public static class BoardGameFactory {
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
}