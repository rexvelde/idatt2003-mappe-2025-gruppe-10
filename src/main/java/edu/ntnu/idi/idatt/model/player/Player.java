package edu.ntnu.idi.idatt.model.player;

import edu.ntnu.idi.idatt.logger.LoggerToFile;
import edu.ntnu.idi.idatt.model.MoveType;
import edu.ntnu.idi.idatt.model.tile.Tile;

import java.util.Objects;
import java.util.logging.Level;

/**
 * The Player class represents a player in the game.
 * It contains the player's name, piece, current tile, and move type.
 * It also provides methods to place the player on a tile and move the player to the next tile.
 */
public class Player {
  private String name;
  private String piece;
  private Tile currentTile;
  private MoveType moveType;

    /**
     * Constructor for the Player class.
     *
     * @param name  The name of the player.
     * @param piece The piece of the player.
     * @throws IllegalArgumentException if name or piece is null or empty.
     */
  public Player(String name, String piece) throws IllegalArgumentException {
    if (name == null || name.isEmpty()) {
      LoggerToFile.log(Level.WARNING, "Player name cannot be null or empty", getClass());
      throw new IllegalArgumentException("Player name cannot be null or empty");
    }
    if (piece == null || piece.isEmpty()) {
      LoggerToFile.log(Level.WARNING, "Player piece cannot be null or empty", getClass());
      throw new IllegalArgumentException("Player piece cannot be null or empty");
    }

    this.name = name;
    this.piece = piece;
    this.moveType = MoveType.STAND_STILL;
  }

    /**
     * Places the player on a tile.
     *
     * @param tile The tile to place the player on.
     * @throws IllegalArgumentException if the tile is null.
     */
  public void placeOnTile(Tile tile) throws IllegalArgumentException {
    if (tile == null) {
      LoggerToFile.log(Level.WARNING, "Tile cannot be null", getClass());
      throw new IllegalArgumentException("Tile must exist");
    }

    this.currentTile = tile;
    if (this.getMoveType() == MoveType.PRIMARY_MOVE) {
      this.currentTile.landPlayer(this);
      LoggerToFile.log(Level.INFO, "Player has landed on tile " + this.currentTile.getTileId(), getClass());
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

    /**
     * Getter for the player's name.
     *
     * @return The name of the player.
     */
  public String getName() {
    return name;
  }

    /**
     * Getter for the player's piece.
     *
     * @return The piece of the player.
     */
  public String getPiece() {
    return piece;
  }

    /**
     * Getter for the current tile of the player.
     *
     * @return The current tile of the player.
     */
  public Tile getCurrentTile() {
    return currentTile;
  }

    /**
     * Setter for the move type of the player.
     *
     * @param moveType The move type to set.
     * @throws IllegalArgumentException if the move type is null.
     */
  public void setMoveType(MoveType moveType) throws IllegalArgumentException {
    if (moveType == null) {
      LoggerToFile.log(Level.WARNING, "Move type cannot be null", getClass());
      throw new IllegalArgumentException("Move type cannot be null");
    }

    this.moveType = moveType;
  }

    /**
     * Getter for the move type of the player.
     *
     * @return The move type of the player.
     */
  public MoveType getMoveType() {
    return moveType;
  }

  /**
   * String concatenation for the player.
   * @return String representation of the player.
   */
  @Override
  public String toString() {
    return "Player{ Name: " + name + ", Piece: " + piece + ", Current tile: " + currentTile.tileId + " }";
  }
}
