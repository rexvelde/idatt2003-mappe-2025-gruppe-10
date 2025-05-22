package edu.ntnu.idi.idatt.model.player;

import edu.ntnu.idi.idatt.model.MoveType;
import edu.ntnu.idi.idatt.model.tile.Tile;

import java.util.Objects;

public class Player {
  private String name;
  private String piece;
  private Tile currentTile;
  private MoveType moveType;

  public Player(String name, String piece) throws IllegalArgumentException {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Player name cannot be null or empty");
    }
    if (piece == null || piece.isEmpty()) {
      throw new IllegalArgumentException("Player piece cannot be null or empty");
    }

    this.name = name;
    this.piece = piece;
    this.currentTile = new Tile(0);
    this.moveType = MoveType.STAND_STILL;
  }

  public void placeOnTile(Tile tile) throws IllegalArgumentException {
    if (tile == null) {
      throw new IllegalArgumentException("Tile must exist");
    }

    this.currentTile = tile;
    if (this.getMoveType() == MoveType.PRIMARY_MOVE) {
      this.currentTile.landPlayer(this);
    }
    this.setMoveType(MoveType.STAND_STILL);
  }

  public String getName() {
    return name;
  }

  public String getPiece() {
    return piece;
  }

  public Tile getCurrentTile() {
    return currentTile;
  }

  public void setMoveType(MoveType moveType) throws IllegalArgumentException {
    if (moveType == null) {
      throw new IllegalArgumentException("Move type cannot be null");
    }

    this.moveType = moveType;
  }

  public MoveType getMoveType() {
    return moveType;
  }

  @Override
  public String toString() {
    return "Player{ Name: " + name + ", Piece: " + piece + ", Current tile: " + currentTile.tileId + " }";
  }
}
