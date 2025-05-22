package edu.ntnu.idi.idatt.model;

import edu.ntnu.idi.idatt.model.board.Board;
import edu.ntnu.idi.idatt.model.tile.Tile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class BoardTest {
    Board board;

    @BeforeEach
    void setUp() {
        board = new Board(90);
    }

    @AfterEach
    void tearDown() {
        board = null;
    }

    @Test
    void testValidAddTileIncrementsSize() {
        int countBefore = board.tiles.size();
        board.addTile(new Tile(-1));
        int countAfter = board.tiles.size();
        assertEquals(countBefore + 1, countAfter);
    }

    @Test
    void testRepeatTileInAddTileDontAffectSize() {
        int countBefore = board.tiles.size();
        board.addTile(new Tile(0));
        int countAfter = board.tiles.size();
        assertEquals(countBefore, countAfter);
    }

    @Test
    void testInvalidAddTileDontAffectSize() {
        int countBefore = board.tiles.size();
        board.addTile(null);
        int countAfter = board.tiles.size();
        assertEquals(countBefore, countAfter);
    }

    @Test
    void getTile() {
    }
}