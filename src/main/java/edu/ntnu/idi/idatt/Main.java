package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.exception.InvalidBoardException;
import edu.ntnu.idi.idatt.exception.PlayerFileFormatException;
import edu.ntnu.idi.idatt.model.BoardGame;
import edu.ntnu.idi.idatt.model.BoardGameFactory;
import edu.ntnu.idi.idatt.model.CsvPlayerFileHandler;
import edu.ntnu.idi.idatt.model.Player;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    try {
      URL boardUrl = Main.class.getClassLoader().getResource("boards/board2.json");
      if (boardUrl == null) {
        throw new FileNotFoundException("Resource boards/board2.json not found.");
      }
      Path boardPath = Paths.get(boardUrl.toURI());

      BoardGameFactory factory = new BoardGameFactory();
      BoardGame game = factory.createBoardGameFromFile(boardPath);

      URL playersUrl = Main.class.getClassLoader().getResource("players/players.csv");
      if (playersUrl == null) {
        throw new FileNotFoundException("Resource players/players.csv not found.");
      }
      Path playersPath = Paths.get(playersUrl.toURI());

      CsvPlayerFileHandler csvPlayerFileHandler = new CsvPlayerFileHandler();
      List<Player> players = csvPlayerFileHandler.readPlayers(playersPath, game.board);

      players.forEach(game::addPlayer);

      game.play();

    } catch (InvalidBoardException | PlayerFileFormatException e) {
      System.err.println("Error during game initialization: " + e.getMessage());
    } catch (FileNotFoundException | URISyntaxException e) {
      System.err.println("Resource not found or invalid URI: " + e.getMessage());
    }
  }
}