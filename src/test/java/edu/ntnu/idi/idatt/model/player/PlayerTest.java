package edu.ntnu.idi.idatt.model.player;

import edu.ntnu.idi.idatt.model.MoveType;
import edu.ntnu.idi.idatt.model.tile.Tile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
  Player player;
  Tile tile;
  Tile targetTile;

  @BeforeEach
  void setUp() {
    player = new Player("Player", "Image");
    tile = new Tile(0);
    targetTile = new Tile(1);
    tile.setNextTile(targetTile);
    tile.setLandAction(targetTile.getLandAction());
  }

  @AfterEach
  void tearDown() {
    player = null;
    tile = null;
    targetTile = null;
  }

  @Test
  void placeOnValidTileAndGetCurrentTile() {
    assertNotEquals(tile, player.getCurrentTile());
    player.placeOnTile(tile);
    assertEquals(tile, player.getCurrentTile());
  }

  @Test
  void placeOnInvalidTile() {
    assertThrows(IllegalArgumentException.class, () -> player.placeOnTile(null));
  }

  @Test
  void getName() {
    assertEquals("Player", player.getName());
    assertNotEquals(null, player.getName());
    assertNotEquals("", player.getName());
  }

  @Test
  void getPiece() {
    assertEquals("Image", player.getPiece());
    assertNotEquals(null, player.getPiece());
    assertNotEquals("", player.getPiece());
  }

  @Test
  void setMoveTypeAndGetMoveType() {
    player.setMoveType(MoveType.PRIMARY_MOVE);
    assertEquals(MoveType.PRIMARY_MOVE, player.getMoveType());
    player.setMoveType(MoveType.SECONDARY_MOVE);
    assertEquals(MoveType.SECONDARY_MOVE, player.getMoveType());
    player.setMoveType(MoveType.STAND_STILL);
    assertEquals(MoveType.STAND_STILL, player.getMoveType());
    assertThrows(IllegalArgumentException.class, () -> player.setMoveType(null));
  }
}