package edu.ntnu.idi.idatt.model;

import java.util.Objects;

public class Player {
  private String name;
  private Tile currentTile;

  public Player(String name, Tile staringTile) {
    this.name = Objects.requireNonNull(name, "Name cannot be null!");
    this.currentTile = Objects.requireNonNull(staringTile, "Starting tile cannot be null!");
  }

    public void placeOnTile(Tile tile) {
        this.currentTile = Objects.requireNonNull(tile, "Tile cannot be null!");
    }

    public void move(int steps) {
       if (steps < 0) {
           throw new IllegalArgumentException("Steps cannot be negative!");
       }
       for (int i = 0; i < steps; i++) {

       }
    }
}
