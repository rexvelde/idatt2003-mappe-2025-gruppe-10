package edu.ntnu.idi.idatt.model.tile;

import edu.ntnu.idi.idatt.model.action.LadderAction;
import edu.ntnu.idi.idatt.model.Player;

public class Tile {
    public int nextTile;
    public int tileId;
    public int landAction;
    // public LadderAction action;

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

    public void setNextTile(int nextTile) {
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
