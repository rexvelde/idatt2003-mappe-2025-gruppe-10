package edu.ntnu.idi.idatt.model.tile;

import edu.ntnu.idi.idatt.model.player.Player;

public interface TileAction {
  public default void perform(Player player) {};
}
