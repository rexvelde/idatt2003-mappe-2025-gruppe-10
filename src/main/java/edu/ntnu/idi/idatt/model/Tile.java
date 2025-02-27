package edu.ntnu.idi.idatt.model;

public class Tile {
    public Tile nextTile;
    public int tileId;
    public TileAction landAction;

    public Tile(int tileId) {
        this.tileId = tileId;
    }

    public void landPlayer(Player player) {
        // TODO: fill landPlayer
    }

    public void leavePlayer(Player player) {
        // TODO: fill leavePlayer
    }

    public void setNextTile(Tile nextTile) {
        this.nextTile = nextTile;
    }
}
