package edu.ntnu.idi.idatt.model.action;

import edu.ntnu.idi.idatt.logger.LoggerToFile;
import edu.ntnu.idi.idatt.model.MoveType;
import edu.ntnu.idi.idatt.model.player.Player;
import edu.ntnu.idi.idatt.model.tile.TileAction;

import java.util.logging.Level;

/**
 * LadderAction has not been implemented because of a lot of trouble with JSON/GSON with nested classes.
 */
public class LadderAction implements TileAction {
  private final int destination;
  private final String description;

  public LadderAction(int destinationTileId, String description) {
    this.destination = destinationTileId;
    this.description = description;
  }

  public void perform(Player player) {
    player.setMoveType(MoveType.SECONDARY_MOVE);
    LoggerToFile.log(Level.INFO, "LadderAction has been set to MoveType.SECONDARY_MOVE", getClass());
  };
}
