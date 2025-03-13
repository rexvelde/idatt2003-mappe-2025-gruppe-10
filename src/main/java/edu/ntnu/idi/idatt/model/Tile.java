package edu.ntnu.idi.idatt.model;

import edu.ntnu.idi.idatt.laddergame.LadderAction;

public class Tile {
    public Tile nextTile;
    public int tileId;
    public TileAction landAction;

    public Tile(int tileId) {
        this.tileId = tileId;
    }

    public void landPlayer(Player player) {
        LadderAction ladderAction = new LadderAction(player.getCurrentTile().tileId - 1, "You have been moved to " + tileId);
        ladderAction.perform(player);
    }

    public void leavePlayer(Player player) {
        // TODO: fill leavePlayer
    }

    public void setNextTile(Tile nextTile) {
        this.nextTile = nextTile;
    }
}
