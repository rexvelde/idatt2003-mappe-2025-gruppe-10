package edu.ntnu.idi.idatt.model.action;

import edu.ntnu.idi.idatt.model.MoveType;
import edu.ntnu.idi.idatt.model.board.Board;
import edu.ntnu.idi.idatt.model.player.Player;
import edu.ntnu.idi.idatt.model.tile.Tile;
import edu.ntnu.idi.idatt.model.tile.TileAction;

public class LadderAction implements TileAction {
  private final Board board;
  private final int destination;
  private final String description;

  public LadderAction(Board board, int destinationTileId, String description) {
    this.board = board;
    this.destination = destinationTileId;
    this.description = description;
  }

  @Override
  public void perform(Player player) {
    Tile destinationTile = board.getTile(destination);
    player.setMoveType(MoveType.SECONDARY_MOVE);
    player.move(destinationTile);
  };
}
