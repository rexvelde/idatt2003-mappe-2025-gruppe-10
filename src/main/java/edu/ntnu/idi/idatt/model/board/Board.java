package edu.ntnu.idi.idatt.model.board;

import edu.ntnu.idi.idatt.logger.LoggerToFile;
import edu.ntnu.idi.idatt.model.tile.Tile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

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
            LoggerToFile.log(Level.INFO, "Tile has been stored", getClass());
        } else {
            LoggerToFile.log(Level.WARNING, "Tile has collided with a stored tile", getClass());
        }
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
