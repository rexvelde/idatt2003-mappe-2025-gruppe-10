package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.exception.InvalidBoardException;
import edu.ntnu.idi.idatt.exception.PlayerFileFormatException;
import edu.ntnu.idi.idatt.model.Board;
import edu.ntnu.idi.idatt.model.BoardGame;
import edu.ntnu.idi.idatt.model.BoardGameFactory;
import edu.ntnu.idi.idatt.model.CsvPlayerFileHandler;
import edu.ntnu.idi.idatt.model.Player;
import java.nio.file.Path;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    try {
      BoardGameFactory factory = new BoardGameFactory();

      BoardGame game = factory.createBoardGameFromFile(Path.of("boards/Board2.json"));

      CsvPlayerFileHandler csvPlayerFileHandler = new CsvPlayerFileHandler();
      List<Player> players = csvPlayerFileHandler.readPlayers(Path.of("players/players.csv"), game.board);

      players.forEach(game::addPlayer);

      game.play();
    } catch (InvalidBoardException | PlayerFileFormatException e) {
      System.err.println("Error during game initialization: " + e.getMessage());
    }
  }
}