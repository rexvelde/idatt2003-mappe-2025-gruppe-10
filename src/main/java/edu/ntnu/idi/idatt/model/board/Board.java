package edu.ntnu.idi.idatt.model.board;

import edu.ntnu.idi.idatt.model.tile.Tile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    public Map<Integer, Tile> tiles;

    public Board(int size) {
        this.tiles = new HashMap<>();
        for (int i = 1; i <= size; i++) {
            addTile(new Tile(i));
        }
    }

    public Board(List<Tile> tiles) {
        this.tiles = new HashMap<>();
        tiles.forEach(this::addTile);
    }

    public void addTile(Tile tile) {
        if (tile == null) {
            return;
        }
        boolean collisions = tiles.values().stream().map(Tile::getTileId).anyMatch(tileId -> tileId == tile.getTileId());
        if (!collisions) {
            tiles.put(tile.getTileId(), tile);
        }
        tile.setBoard(this);
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
