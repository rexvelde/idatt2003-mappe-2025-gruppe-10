package edu.ntnu.idi.idatt.model;

import java.util.Objects;

public class Player {
  private String name;
  private String piece;
  private Tile currentTile;

  public Player(String name, String piece) {
    this.name = Objects.requireNonNull(name, "Name cannot be null!");
    this.piece = Objects.requireNonNull(piece, "Piece cannot be null!");
    this.currentTile = new Tile(0);
  }

  public void placeOnTile(Tile tile) {
    this.currentTile = Objects.requireNonNull(tile, "Tile cannot be null!");
    this.currentTile.landPlayer(this);
  }

  public void move(int steps) {
    if (steps < 0) {
      throw new IllegalArgumentException("Steps cannot be negative!");
    }
    for (int i = 0; i < steps; i++) {
      // Pass
    }
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

  @Override
  public String toString() {
    return "Player{ Name: " + name + ", Piece: " + piece + ", Current tile: " + currentTile.tileId + " }";
  }
}
