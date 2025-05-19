package edu.ntnu.idi.idatt.model;

/**
 * MoveType is a way to differ between which sort of action that takes place.
 * This is because we found no obvious way to only call `LadderAction´ once
 * when the player throws the dice.
 */
public enum MoveType {
  STAND_STILL,
  PRIMARY_MOVE,
  SECONDARY_MOVE
}
