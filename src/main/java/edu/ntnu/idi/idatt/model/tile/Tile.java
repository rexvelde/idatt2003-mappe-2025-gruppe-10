package edu.ntnu.idi.idatt.model.tile;

import edu.ntnu.idi.idatt.model.action.LadderAction;
import edu.ntnu.idi.idatt.model.player.Player;

public class Tile {
    public int nextTileId;
    public int tileId;
    public int landAction;

    // Thanks, ChatGPT, for letting me know about transient types.
    public transient Tile nextTile;
    public transient LadderAction action;

    public Tile(int tileId) {
        this.tileId = tileId;
    }

    public void landPlayer(Player player) {
        LadderAction ladderAction = new LadderAction(player.getCurrentTile().getLandAction(), "You have been moved to " + tileId);
        ladderAction.perform(player);
        System.out.println("Player " + player.getName() + " landing at " + tileId);
    }

    public void leavePlayer(Player player) {
        // TODO: fill leavePlayer
    }

    public void setNextTile(Tile nextTile) {
        this.nextTile = nextTile;
    }

    public int getTileId() {
        return tileId;
    }

    public int getLandAction() {
        return landAction;
    }

    public void setLandAction(int jumpValue) {
        this.landAction = jumpValue;
    }

    public boolean isLandAction() {
        return landAction != 0;
    }
}
