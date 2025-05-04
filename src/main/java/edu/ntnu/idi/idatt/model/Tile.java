package edu.ntnu.idi.idatt.model;

import edu.ntnu.idi.idatt.laddergame.LadderAction;

public class Tile {
    public int nextTile;
    public int tileId;
    public int landAction;
    public LadderAction action;

    public Tile(int tileId) {
        this.tileId = tileId;
    }

    public void landPlayer(Player player) {
        // LadderAction ladderAction = new LadderAction(player.getCurrentTile().tileId - 1, "You have been moved to " + tileId);
        // ladderAction.perform(player);
    }

    public void leavePlayer(Player player) {
        // TODO: fill leavePlayer
    }

    public void setNextTile(int nextTile) {
        this.nextTile = nextTile;
    }

    public int getTileId() {
        return tileId;
    }
}
