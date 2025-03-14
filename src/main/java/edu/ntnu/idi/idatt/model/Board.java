package edu.ntnu.idi.idatt.model;

import java.util.HashMap;
import java.util.Map;

public class Board {
    public Map<Integer, Tile> tiles;

    public Board() {
        this.tiles = new HashMap<>();
        for (int i = 0; i <= 90; i++) {
            addTile(new Tile(i));
        }
    }

    public void addTile(Tile tile) {
        if (tiles == null) {
            return;
        }
        tiles.put(tile.tileId, tile);
    }

    public Tile getTile(int tileId) {
        return tiles.get(tileId);
    }

    /**
     * Accessor method to get the ID of the last tile on the board.
     */
    public int getMaxTileId() {
        return tiles.keySet().stream().mapToInt(Integer::intValue).max().orElse(0);
    }
}
