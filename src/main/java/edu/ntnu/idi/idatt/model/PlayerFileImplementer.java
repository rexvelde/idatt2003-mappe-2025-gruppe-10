package edu.ntnu.idi.idatt.model;

import java.io.BufferedWriter;

public class PlayerFileImplementer implements PlayerFileHandler {

  @Override
  public void writePlayersToCSV(List<Player> players, String filePath) throws IOException {
    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {

    }
}
