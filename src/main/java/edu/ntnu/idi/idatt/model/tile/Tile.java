package edu.ntnu.idi.idatt.model.tile;

import edu.ntnu.idi.idatt.logger.LoggerToFile;
import edu.ntnu.idi.idatt.model.action.LadderAction;
import edu.ntnu.idi.idatt.model.board.Board;
import edu.ntnu.idi.idatt.model.player.Player;

import java.util.logging.Level;

public class Tile {
    public int nextTileId;
    public int tileId;
    public int landAction;

    private Board board;

    public transient Tile nextTile;
    public transient LadderAction action;

    public Tile(int tileId) throws IllegalArgumentException {
        if (tileId < 0) {
            LoggerToFile.log(Level.WARNING, "Tile cannot be negative", getClass());
            throw new IllegalArgumentException("Tile id cannot be negative");
        }
        this.tileId = tileId;
    }

    public void landPlayer(Player player) throws IllegalArgumentException {
        if (player == null) {
            LoggerToFile.log(Level.WARNING, "Player cannot be null", getClass());
            throw new IllegalArgumentException("Player cannot be null");
        }
      LadderAction ladderAction = new LadderAction(player.getCurrentTile().getBoard(), player.getCurrentTile().getLandAction(), "You have been moved to " + tileId);
    }

    /**
     * leavePlayer should perform an action when player leaves.
     * However, we did not find a good use for this, and therefore have not implemented it.
     *
     * @param player Player to be performed some action on.
     */
    public void leavePlayer(Player player) {
        // TODO: fill leavePlayer
    }

    public void setNextTile(Tile nextTile) throws IllegalArgumentException {
        if (nextTile == null || nextTile.getTileId() < 0) {
            LoggerToFile.log(Level.WARNING, "Next tile id must exist and be positive", getClass());
            throw new IllegalArgumentException("Next tile id must exist and be positive");
        }
        this.nextTile = nextTile;
        this.nextTileId = nextTile.getTileId();
    }

    public int getTileId() {
        return tileId;
    }

    public int getLandAction() {
        return landAction;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setLandAction(int jumpValue) throws IllegalArgumentException {
        if (jumpValue < 0) {
            LoggerToFile.log(Level.WARNING, "Jump value must be a positive value", getClass());
            throw new IllegalArgumentException("Jump value must be a positive value");
        }
        this.landAction = jumpValue;
    }

    public boolean isLandAction() {
        return landAction != 0;
    }

    @Override
    public String toString() {
        return "Tile [tileId=" + tileId + ", landAction=" + landAction + "]";
    }
}
