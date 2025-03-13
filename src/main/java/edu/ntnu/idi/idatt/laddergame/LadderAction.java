package edu.ntnu.idi.idatt.laddergame;

import edu.ntnu.idi.idatt.model.Player;
import edu.ntnu.idi.idatt.model.TileAction;

public class LadderAction implements TileAction {
  private int destination;
  private String description;

  public LadderAction(int destinationTileId, String description) {
    this.destination = destinationTileId;
    this.description = description;
  }

  public void perform(Player player) {
    System.out.println("Ladder action performed!");
    player.move(destination);
  };
}
