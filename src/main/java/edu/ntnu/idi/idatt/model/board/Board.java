package edu.ntnu.idi.idatt.model.board;

import edu.ntnu.idi.idatt.logger.LoggerToFile;
import edu.ntnu.idi.idatt.model.tile.Tile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class Board {
    public Map<Integer, Tile> tiles;

    /**
     * Constructor for the Board class.
     * Initializes the board with a given size and creates tiles from 1 to size.
     *
     * @param size The size of the board, which determines the number of tiles.
     */
    public Board(int size) {
        this.tiles = new HashMap<>();
        for (int i = 1; i <= size; i++) {
            addTile(new Tile(i));
        }
    }

    /**
     * Constructor for the Board class.
     * Initializes the board with a given list of tiles.
     *
     * @param tiles The list of tiles to be added to the board.
     */
    public Board(List<Tile> tiles) {
        this.tiles = new HashMap<>();
        tiles.forEach(this::addTile);
    }

    /**
     * Adds a tile to the board.
     * If the tile is null or if a tile with the same ID already exists, it will not be added.
     *
     * @param tile The tile to be added to the board.
     */
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
        tile.setBoard(this);
        tiles.put(tile.tileId, tile);
    }

    /**
     * Getter for the tiles on the board.
     *
     * @param tileId The tile.
     */
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
