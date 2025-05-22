package edu.ntnu.idi.idatt.model.board;

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
    board.addTile(new Tile(91));
    int countAfter = board.tiles.size();
    assertEquals(countBefore + 1, countAfter);
  }

  @Test
  void testInvalidAddTileThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> board.addTile(new Tile(-1)));
  }

  @Test
  void testRepeatTileInAddTileDontGetAdded() {
    int countBefore = board.tiles.size();
    board.addTile(new Tile(1));
    int countAfter = board.tiles.size();
    assertEquals(countBefore, countAfter);
  }

  @Test
  void testAddNullTileDontGetAdded() {
    int countBefore = board.tiles.size();
    board.addTile(null);
    int countAfter = board.tiles.size();
    assertEquals(countBefore, countAfter);
  }

  @Test
  void getValidTileGivesTile() {
    Tile tile = board.getTile(1);
    assertNotNull(tile);
  }

  @Test
  void getInvalidTileDontGiveTile() {
    Tile tile = board.getTile(0);
    assertNull(tile);
  }
}