package edu.ntnu.idi.idatt.model;

import java.util.HashMap;
import java.util.Map;

public class Board {
    public Map<Integer, Tile> tiles;

    public Board() {
        tiles = new HashMap<>();
    }

    public void addTile(Tile tile) {
        int count = tiles.size();
        tiles.put(count + 1, tile);
    }

    public Tile getTile(int tileId) {
        return tiles.get(tileId);
    }
}
