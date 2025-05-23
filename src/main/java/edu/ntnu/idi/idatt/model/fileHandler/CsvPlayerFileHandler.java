package edu.ntnu.idi.idatt.model.fileHandler;

import edu.ntnu.idi.idatt.exception.InvalidBoardException;
import edu.ntnu.idi.idatt.exception.PlayerFileFormatException;
import edu.ntnu.idi.idatt.logger.LoggerToFile;
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
import java.util.logging.Level;

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
          LoggerToFile.log(Level.WARNING, "Invalid line: " + line, getClass());
          throw new PlayerFileFormatException("Invalid line: " + line);
        }

        String name = parts[0].trim();
        String piece = parts[1].trim();

        Player player = new Player(name, piece);
        players.add(player);
      }

    } catch (IOException e) {
      LoggerToFile.log(Level.WARNING, "Error reading CSV file: " + csvFile, getClass());
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
      LoggerToFile.log(Level.WARNING, "Error writing to CSV file" + csvFile, getClass());
      throw new PlayerFileFormatException("Error writing CSV file: " + csvFile, e);
    }
  }
}