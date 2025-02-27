package edu.ntnu.idi.idatt.model;

import java.io.IOException;
import java.util.List;

public interface PlayerFileHandler {
  void writePlayersToFile(List<Player> players, String filePath) throws IOException;
  List<Player> readPlayersFromFile(String filePath) throws IOException;
}
