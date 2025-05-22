package edu.ntnu.idi.idatt.model.player;

import edu.ntnu.idi.idatt.model.MoveType;
import edu.ntnu.idi.idatt.model.tile.Tile;

import java.util.Objects;

public class Player {
  private String name;
  private String piece;
  private Tile currentTile;
  private MoveType moveType;

  public Player(String name, String piece) {
    this.name = Objects.requireNonNull(name, "Name cannot be null!");
    this.piece = Objects.requireNonNull(piece, "Piece cannot be null!");
    // this.currentTile = new Tile(0);
    this.moveType = MoveType.STAND_STILL;
  }

  public void placeOnTile(Tile tile) {
    this.currentTile = Objects.requireNonNull(tile, "Tile cannot be null!");
    if (this.getMoveType() == MoveType.PRIMARY_MOVE) {
      this.currentTile.landPlayer(this);
    }
    this.setMoveType(MoveType.STAND_STILL);
  }

  /**
   * Moving player to the next tile, as a form of animation.
   *
   * @param nextTile The next tile.
   */
  public void move(Tile nextTile) {
    this.currentTile = Objects.requireNonNull(nextTile);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPiece() {
    return piece;
  }

  public void setPiece(String piece) {
    this.piece = piece;
  }

  public Tile getCurrentTile() {
    return currentTile;
  }

  public void setMoveType(MoveType moveType) {
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
