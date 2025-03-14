package edu.ntnu.idi.idatt.model;

import edu.ntnu.idi.idatt.exception.PlayerFileFormatException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvPlayerFileHandler {

  /**
   * Reads a CSV file with player information and returns a list of players.
   *
   * @param csvFile Path to the CSV file.
   * @param board The board the players will be placed on.
   * @return List of players.
   * @throws PlayerFileFormatException if an error occurs while reading the file.
   */
  public List<Player> readPlayers(Path csvFile, Board board) throws PlayerFileFormatException {
    List<Player> players = new ArrayList<>();

    try (BufferedReader br = Files.newBufferedReader(csvFile)) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts.length != 2) {
          throw new PlayerFileFormatException("Invalid line: " + line);
        }

        String name = parts[0];
        int tileId;

        try {
          tileId = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
          throw new PlayerFileFormatException("Invalid tile id: " + parts[1], e);
        }

        Tile tile = board.getTile(tileId);

        if (tile == null) {
          throw new PlayerFileFormatException("Invalid tile id: " + tileId);
        }

        Player player = new Player(name, tile);
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
        String line = player.getName() + "," + player.getCurrentTile().tileId;
        bw.write(line);
        bw.newLine();
      }
    } catch (IOException e) {
        throw new PlayerFileFormatException("Error writing CSV file: " + csvFile, e);
    }
  }
}