package edu.ntnu.idi.idatt.model.action;

import edu.ntnu.idi.idatt.logger.LoggerToFile;
import edu.ntnu.idi.idatt.model.MoveType;
import edu.ntnu.idi.idatt.model.board.Board;
import edu.ntnu.idi.idatt.model.player.Player;
import edu.ntnu.idi.idatt.model.tile.Tile;
import edu.ntnu.idi.idatt.model.tile.TileAction;

import java.util.logging.Level;

/**
 * LadderAction has not been implemented because of a lot of trouble with JSON/GSON with nested classes.
 */
public class LadderAction implements TileAction {
    private final Board board;
    private final int destination;
    private final String description;

    public LadderAction(Board board, int destinationTileId, String description) {
        this.board = board;
        this.destination = destinationTileId;
        this.description = description;
    }

    /**
     * This method is called when a player lands on a tile with a ladder action.
     * It sets the player's move type to SECONDARY_MOVE and moves the player to the destination tile.
     *
     * @param player The player who landed on the tile.
     */
    @Override
    public void perform(Player player) {
        Tile destinationTile = board.getTile(destination);
        player.setMoveType(MoveType.SECONDARY_MOVE);
        player.move(destinationTile);
        LoggerToFile.log(Level.INFO, "LadderAction has been set to MoveType.SECONDARY_MOVE", getClass());
    };
}
