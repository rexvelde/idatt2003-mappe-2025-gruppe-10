package edu.ntnu.idi.idatt.model.tile;

import edu.ntnu.idi.idatt.model.MoveType;
import edu.ntnu.idi.idatt.model.player.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {
  private Tile tile;
  private Tile nextTile;
  private Tile targetTile;

  private Player originalPlayer;
  private Player newPlayer;
  private Player alteredPlayer;

  @BeforeEach
  void setUp() {
    tile = new Tile(0);
    nextTile = new Tile(1);
    targetTile = new Tile(10);

    originalPlayer = new Player("Player", "Image");
    alteredPlayer = new Player("Player", "Image");
    newPlayer = new Player("NewPlayer", "Image2");

    tile.setNextTile(nextTile);
    tile.setLandAction(10);
  }

  @AfterEach
  void tearDown() {
    tile = null;
    nextTile = null;
    targetTile = null;
    originalPlayer = null;
    newPlayer = null;
  }

  @Test
  void setNextTileIsValidForExistingTile() {
    tile.setNextTile(nextTile);
    assertEquals(tile.nextTile.getTileId(), nextTile.getTileId());
  }

  @Test
  void setNextTileIsInvalidForNullTile() {
    assertThrows(IllegalArgumentException.class, () -> tile.setNextTile(null));
  }

  @Test
  void setNextTileIsInvalidForNegativeTile() {
    assertThrows(IllegalArgumentException.class, () -> tile.setNextTile(new Tile(-1)));
  }

  @Test
  void setNextTileIsInvalidForNonIntegerTile() {
    assertThrows(IllegalArgumentException.class, () -> tile.setNextTile(new Tile(Integer.MAX_VALUE+1)));
  }

  @Test
  void getTileId() {
    assertEquals(0, tile.getTileId());
    assertEquals(1, nextTile.getTileId());
    assertEquals(10, targetTile.getTileId());
  }

  @Test
  void getLandAction() {
    assertEquals(10, tile.getLandAction());
  }

  @Test
  void setLandAction() {
    int oldLandAction = tile.getLandAction();
    assert (oldLandAction == 10);
    tile.setLandAction(1);
    assertEquals(1, tile.getLandAction());
  }

  @Test
  void isLandAction() {
    assertTrue(tile.isLandAction());
    assertFalse(nextTile.isLandAction());
  }
}