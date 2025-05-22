package edu.ntnu.idi.idatt.model.action;

import edu.ntnu.idi.idatt.model.MoveType;
import edu.ntnu.idi.idatt.model.player.Player;
import edu.ntnu.idi.idatt.model.tile.TileAction;

public class LadderAction implements TileAction {
  private final int destination;
  private final String description;

  public LadderAction(int destinationTileId, String description) {
    this.destination = destinationTileId;
    this.description = description;
  }

  public void perform(Player player) {
    System.out.println("Ladder action performed! " + player.getName() + " " + destination);
    player.setMoveType(MoveType.SECONDARY_MOVE);
  };
}
